package com.example.teamup.service;

import com.example.teamup.dto.request.*;
import com.example.teamup.dto.response.TokenResponseDto;
import com.example.teamup.dto.response.SignUpResponseDto;

import java.io.IOException;

public interface UserService {

    SignUpResponseDto signUpWithEmail(SignUpRequestDto signUpRequestDto);
    String verifyOTP(OTPRequest otpRequest);
    TokenResponseDto login(LoginRequestDto loginRequestDto);
    String forgotPassword(ForgotPasswordDto forgotPasswordDto) throws IOException;

    String changePassword(NewPasswordDto newPasswordDto);
    String updatePassword(UpdatePasswordDto updatePasswordDto);
    String updateUsername(UpdateUsernameDto updateUsernameDto);

}
