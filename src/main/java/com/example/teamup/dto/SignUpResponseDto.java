package com.example.teamup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpResponseDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String city;
}
