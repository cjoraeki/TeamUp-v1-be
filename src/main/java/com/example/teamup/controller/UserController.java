package com.example.teamup.controller;

import com.example.teamup.dto.request.*;
import com.example.teamup.dto.response.ApiResponse;
import com.example.teamup.dto.response.SignUpResponseDto;
import com.example.teamup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpToTeamUp(@Valid @RequestBody SignUpRequestDto requestDto){
        userService.signUpWithEmail(requestDto);
        return new ResponseEntity<>("Registration Successful! Check your mail for activation link", HttpStatus.CREATED);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@Valid @RequestBody OTPRequest otpRequest){
        userService.verifyOTP(otpRequest);
        return new ResponseEntity<>("Verification successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginWithEmailOrPhone(@Valid @RequestBody LoginRequestDto loginRequestDto){
        userService.login(loginRequestDto);
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> userForgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto) throws IOException {
        userService.forgotPassword(forgotPasswordDto);
        return new ResponseEntity<>("Check your email to change password", HttpStatus.OK);
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

    @PatchMapping("/update-username")
    public ResponseEntity<String> userToChangeUsername(@Valid @RequestBody UpdateUsernameDto updateUsernameDto){
        userService.updateUsername(updateUsernameDto);
        return new ResponseEntity<>("Updated username successfully", HttpStatus.ACCEPTED);
    }


}
