package com.example.teamup.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @Schema(defaultValue = "cjoraeki3@gmail.com")
    @Email(message = "Enter a valid email or phone number")
    private String email;

    @Schema(defaultValue = "1234")
    @NotNull(message = "Invalid password")
    private String password;

}
