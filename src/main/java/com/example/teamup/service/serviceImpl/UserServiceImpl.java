package com.example.teamup.service.serviceImpl;

import com.example.teamup.configuration.security.AppUserDetailsService;
import com.example.teamup.configuration.security.JwtUtil;
import com.example.teamup.dto.request.*;
import com.example.teamup.dto.response.TokenResponseDto;
import com.example.teamup.dto.response.SignUpResponseDto;
import com.example.teamup.entity.User;
import com.example.teamup.enums.Role;
import com.example.teamup.exception.*;
import com.example.teamup.repository.UserRepository;
import com.example.teamup.service.UserService;
import com.example.teamup.utils.AppUtil;
import com.example.teamup.utils.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager auth;
    private final AuthDetails authDetails;
    private final JwtUtil jwtUtil;
    private final AppUtil app;
    private final UserRepository userRepository;
    private final JavaMailServiceImpl javaMailService;
    private final AppUserDetailsService appUserDetailsService;
    private final HttpServletRequest request;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;


    @Override
    public SignUpResponseDto signUpWithEmail(SignUpRequestDto signUpRequestDto) {
        Boolean emailExist = userRepository.existsByEmail(signUpRequestDto.getEmail());
        if (emailExist) throw new AlreadyExistsException("Email already exists", "Login to your account");

        User user = new User();
        user.setFirstName(signUpRequestDto.getFirstName());
        user.setLastName(signUpRequestDto.getLastName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setRole(Role.USER);
        user.setUsername(signUpRequestDto.getEmail().substring(0,signUpRequestDto.getEmail().indexOf('@')));
        user.setVerificationStatus(false);
        user.setFavoriteSports(signUpRequestDto.getFavoriteSports());

        String validToken = jwtUtil.generateEmailVerificationToken(user.getEmail());
        user.setTokenForEmail(validToken);

        String url = "http://" + request.getServerName() + ":8080" + "/api/v1/verify-email?token="
                + validToken + "&email="+ signUpRequestDto.getEmail();

        String subject = "Verify your email address";

        String message =
                "<html> " +
                    "<body>" +
                        "<h4>Hi " + user.getFirstName() + " " + user.getLastName() +",</h4> \n" +
                        "<p>Welcome to TeamUp Sports.\n" +
                        "To activate your TeamUp Account, verify your email address by clicking " +
                        "<a href="+url+">verify</a></p>" +
                    "</body> " +
                "</html>";

        javaMailService.sendMailAlt(signUpRequestDto.getEmail(), subject, message);

//        String validOTP = (jwtUtil.generateOTPToken());
        String validOTP = "12345";

        user.setValidOTP(passwordEncoder.encode(validOTP));

        userRepository.save(user);


//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

//        int len = signUpRequestDto.getPhoneNumber().length();
//        String p = "+234"+signUpRequestDto.getPhoneNumber().substring(1,len);
//
//        Message messagePhone = Message.creator(new PhoneNumber(p),
//                new PhoneNumber("+15074287075"),
//                "Your OTP for TeamUp account is: " + validOTP + ". \nIt expires in 15 minutes.\n").create();

//        System.out.println(messagePhone.getSid());
//        System.out.println("Twilio++++++++"+"+234"+ p);

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        BeanUtils.copyProperties(user, signUpResponseDto);

        return signUpResponseDto;
    }

    @Override
    public String verifyOTP(OTPRequest otpRequest) {
        User user = userRepository.findByEmail(otpRequest.getEmailForOTP()).get();
        if (user.isVerificationStatus()){
            return "This account is already verified";
        }

        if (passwordEncoder.matches(otpRequest.getOtp(), user.getValidOTP())){
            user.setVerificationStatus(true);
            userRepository.save(user);
            return "Verification successful";
        }else {
            return "Check the OTP and try again";
        }
    }

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {

        try {
            Authentication authentication = auth.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String email = app.validEmail(loginRequestDto.getEmail()) ? loginRequestDto.getEmail() : null;
                User user  = userRepository.findByEmailOrPhoneNumber(email, loginRequestDto.getEmail())
                        .orElseThrow();

                if (!user.isVerificationStatus())
                    throw new ValidationException("User not verified");

                logger.info("Generating access token for {}", user.getEmail());

                UserDetails userDetails = appUserDetailsService.loadUserByUsername(loginRequestDto.getEmail());
                String accessToken = jwtUtil.generateToken(userDetails);
                return TokenResponseDto.builder()
                        .token(accessToken)
                        .firstName(user.getFirstName())
                        .LastName(user.getLastName())
                        .favoriteSports(user.getFavoriteSports())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .phoneNumber(user.getPhoneNumber())
                        .verificationStatus(true)
                        .build();

                } else {
                    throw new AuthenticationException("Invalid username or password");
                }

        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public String changePassword(NewPasswordDto newPasswordDto) {
        User user = userRepository.findByEmail(newPasswordDto.getNewEmail())
                .orElseThrow(() -> new UserNotFoundException("This User does not exist."));


        user.setPassword(newPasswordDto.getNewPassword());
        userRepository.save(user);

        return "Password successfully changed";
    }

    @Override
    public String updatePassword(UpdatePasswordDto updatePasswordDto) {
        User user = authDetails.getAuthorizedUser();
        if (user==null){
            throw new UserNotFoundException("User not logged in", "Login to continue");
        }
        user.setPassword(updatePasswordDto.getUpdatePassword());
        userRepository.save(user);
        return "Password updated";
    }

    @Override
    public TokenResponseDto updateProfile(UpdateUsernameDto updateUsernameDto) {
        User user = authDetails.getAuthorizedUser();
        if (user == null) {
            throw new UserNotFoundException("User not logged in", "Please log in to continue.");
        }
        if (updateUsernameDto == null || updateUsernameDto.getUsername() == null || updateUsernameDto.getUsername().isEmpty()) {
            throw new InvalidInputException("Invalid input", "Please provide a valid username.");
        }
        if (userRepository.existsByEmail(updateUsernameDto.getUsername())) {
            throw new AlreadyExistsException("Username already exists", "Please choose a different username.");
        }

        user.setUsername(updateUsernameDto.getUsername().trim() != null ? updateUsernameDto.getUsername().trim() : user.getUsername());
        userRepository.save(user);

        return TokenResponseDto.builder()
                .firstName(user.getFirstName())
                .LastName(user.getLastName())
                .favoriteSports(user.getFavoriteSports())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .verificationStatus(true)
                .build();
    }

    @Override
    public String fetchUsername() {
        return authDetails.getAuthorizedUser().getUsername();
    }

    @Override
    public String verifyEmail(String email, String token) {
        User user = userRepository.findByEmail(email).get();

        if (user.isVerificationStatus()) {
            return "This account is already verified";
        }

        if (token.equals(user.getTokenForEmail())) {
            return "Email verified. Enter OTP to verify phone number";
        }
        return "Invalid token";
    }
}
