package com.example.teamup.service;

import com.example.teamup.dto.request.*;
import com.example.teamup.dto.response.TokenResponseDto;
import com.example.teamup.dto.response.SignUpResponseDto;

import java.io.IOException;

public interface UserService {

    SignUpResponseDto signUpWithEmail(SignUpRequestDto signUpRequestDto);
    String verifyOTP(OTPRequest otpRequest);
    TokenResponseDto login(LoginRequestDto loginRequestDto);
    String changePassword(NewPasswordDto newPasswordDto);
    String updatePassword(UpdatePasswordDto updatePasswordDto);
    TokenResponseDto updateProfile(UpdateUsernameDto updateUsernameDto);
    String fetchUsername();
    String verifyEmail(String email, String token);
}
