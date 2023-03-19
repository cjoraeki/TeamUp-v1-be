package com.example.teamup.dto.response;


import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
    private String token;

    private String firstName;

    private String LastName;

    private String image;

    private String phoneNumber;

    private String email;

    private boolean verificationStatus;

    private String favoriteSports;

    private String username;

}
