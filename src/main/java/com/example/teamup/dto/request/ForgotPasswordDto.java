package com.example.teamup.dto.request;


import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class ForgotPasswordDto {

    @NotBlank(message = "Email is mandatory")
    private String email;
}
