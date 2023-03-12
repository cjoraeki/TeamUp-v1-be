package com.example.teamup.entity;


import com.example.teamup.enums.Role;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
//@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Admin extends Person{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "person_id")
//    private Person person;
}
