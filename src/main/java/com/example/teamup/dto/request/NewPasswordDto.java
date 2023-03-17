package com.example.teamup.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class NewPasswordDto {
    @NotBlank(message = "Password is mandatory")
    private String newPassword;

    private String newEmail;

}
