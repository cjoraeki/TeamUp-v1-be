package com.example.teamup.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @Email(message = "Enter a valid email or phone number")
    private String email;
    private String phone;
    @NotNull(message = "Invalid password")
//    @Size(min = 8)
    private String password;

}
