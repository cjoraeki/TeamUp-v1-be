package com.example.teamup.entity;

import com.example.teamup.enums.Gender;
import com.example.teamup.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @NotNull(message = "Firstname cannot be empty")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotNull(message = "Lastname cannot be empty")
    @Column(nullable = false, length = 50)
    private String LastName;

//    private String image;

    @NotNull(message = "Phone number cannot be empty")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean verificationStatus;

    @Column(unique = true)
    private String uuid;

//    @Enumerated(EnumType.STRING)
//    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String validOTP;

    private String tokenForEmail;

    private String favoriteSports;

    private String username;
}
