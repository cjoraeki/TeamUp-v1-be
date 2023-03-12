package com.example.teamup.entity;

import com.example.teamup.enums.Gender;
import com.example.teamup.enums.Role;
import com.example.teamup.enums.Sports;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table
public class User extends Person{
    private String city;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sports sports;

}
