package com.example.teamup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Setter
@Getter
//@Builder
//@Entity
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
//@Table
public abstract class Person extends BaseEntity{
    @NotNull(message = "Firstname cannot be empty")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotNull(message = "Lastname cannot be empty")
    @Column(nullable = false, length = 50)
    private String LastName;

    private String profilePic;

    @NotNull(message = "Phone number cannot be empty")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean verificationStatus;

    private boolean isActive = true;

//    @JsonIgnore
//    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
//    private Admin admin;
//
//    @JsonIgnore
//    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
//    private User user;
}
