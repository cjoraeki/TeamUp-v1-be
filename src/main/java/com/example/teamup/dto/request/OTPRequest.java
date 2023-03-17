package com.example.teamup.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class OTPRequest {
    @NotNull(message = "Enter 5-digit OTP sent to your phone")
    @Size(min = 5, max = 5)
    private String otp;

    private String emailForOTP;
}
