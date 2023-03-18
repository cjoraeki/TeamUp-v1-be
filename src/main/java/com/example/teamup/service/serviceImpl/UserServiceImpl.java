package com.example.teamup.service.serviceImpl;

import com.example.teamup.configuration.security.AppUserDetailsService;
import com.example.teamup.configuration.security.JwtUtil;
import com.example.teamup.dto.request.*;
import com.example.teamup.dto.response.TokenResponseDto;
import com.example.teamup.dto.response.SignUpResponseDto;
import com.example.teamup.entity.User;
import com.example.teamup.enums.Role;
import com.example.teamup.exception.AlreadyExistsException;
import com.example.teamup.exception.AuthenticationException;
import com.example.teamup.exception.UserNotFoundException;
import com.example.teamup.exception.ValidationException;
import com.example.teamup.repository.UserRepository;
import com.example.teamup.service.UserService;
import com.example.teamup.utils.AppUtil;
import com.example.teamup.utils.AuthDetails;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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
import java.io.IOException;
import java.util.Date;


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
    public static final String ACCOUNT_SID = "ACcf214dcd245e4bd8112c8d38dc499064";
    public static final String AUTH_TOKEN = "64469ea18feac687619a4b6fd69721e9";


    @Override
    public SignUpResponseDto signUpWithEmail(SignUpRequestDto signUpRequestDto) {
        Boolean emailExist = userRepository.existsByEmail(signUpRequestDto.getEmail());
        if (emailExist){
            throw new AlreadyExistsException("Email already exists", "Login to your account");
        }

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

        String url = "https://" + request.getServerName() + ":3000" + "/otp?token="
                + validToken;

        String subject = "Verify your email address";

        String message =
                "<html> " +
                    "<body>" +
                        "<h4>Hi " + user.getFirstName() + " " + user.getLastName() +",</h4> \n" +
                        "<p>Welcome to TeamUp Sports.\n" +
                        "To activate your TeamUp Account, verify your email address by clicking " +
                        "<a href="+url+">VERIFY</a></p>" +
                    "</body> " +
                "</html>";

        javaMailService.sendMailAlt(signUpRequestDto.getEmail(), subject, message);

        String validOTP = (jwtUtil.generateOTPToken());

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
                        .build();

                } else {
                    throw new AuthenticationException("Invalid username or password");
                }

        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public String forgotPassword(ForgotPasswordDto forgotRequest) throws IOException {

        User person = userRepository.findByEmail(forgotRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("This User does not exist."));

        String tokenGenerated = jwtUtil.generateToken(appUserDetailsService.loadUserByUsername(person.getEmail()));

        javaMailService.sendMail(forgotRequest.getEmail(), "This is a request to reset your password",
                "This is your reset link which expires in 15 minutes: http://localhost:3000/reset-password/" + tokenGenerated);

        return "Kindly, check your email for password reset instructions!";
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
    public String updateUsername(UpdateUsernameDto updateUsernameDto) {
        User user = authDetails.getAuthorizedUser();
        if (user==null){
            throw new UserNotFoundException("User not logged in", "Login to continue");
        }
        user.setUsername(updateUsernameDto.getNewUsername());
        userRepository.save(user);
        return "Username changed";
    }


}
