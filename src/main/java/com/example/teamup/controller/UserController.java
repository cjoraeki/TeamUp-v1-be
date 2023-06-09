package com.example.teamup.controller;

import com.example.teamup.dto.request.*;
import com.example.teamup.dto.response.SignUpResponseDto;
import com.example.teamup.dto.response.TokenResponseDto;
import com.example.teamup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUpToTeamUp(@Valid @RequestBody SignUpRequestDto requestDto){
        return new ResponseEntity<>(userService.signUpWithEmail(requestDto), HttpStatus.valueOf(201));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyToken(@Valid @RequestBody OTPRequest otpRequest){
        return new ResponseEntity<>(userService.verifyOTP(otpRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginWithEmailOrPhone(@Valid @RequestBody LoginRequestDto loginRequestDto){
        TokenResponseDto tokenResponseDto = userService.login(loginRequestDto);
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> userToChangePassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        userService.changePassword(newPasswordDto);
        return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> userToChangePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto){
        userService.updatePassword(updatePasswordDto);
        return new ResponseEntity<>("Updated password successfully", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<TokenResponseDto> userChangeProfile(@RequestBody UpdateUsernameDto updateUsernameDto){
        return new ResponseEntity<>(userService.updateProfile(updateUsernameDto), HttpStatus.OK);
    }

    @GetMapping("/fetch-username")
    public ResponseEntity<String> getProfileUsername(){
        return new ResponseEntity<>(userService.fetchUsername(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/verify-email")
    public RedirectView verifyEmailOfNewUser(@Valid @RequestParam String token, @Valid @RequestParam String email){
        String verificationResult = userService.verifyEmail(email, token);

        if (verificationResult.equals("Email verified. Enter OTP to verify phone number")) {
            System.out.println("Redirecting page here +++++");
            return new RedirectView("http://localhost:3000/otp?token=" + token + "&email=" + email);
        }

        return new RedirectView("http://localhost:3000/signup");
    }

}
