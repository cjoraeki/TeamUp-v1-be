package com.example.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;
    @NotNull(message = "Lastname cannot be empty")
    private String lastName;
    @NotNull(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Password cannot be blank")
    private String password;
    private String city;
}
