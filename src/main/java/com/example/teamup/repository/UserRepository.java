package com.example.teamup.repository;

import com.example.teamup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrPhoneNumber(String email, String phone);
    Optional<User> findByPhoneNumber(String phone);
    Boolean existsByEmail(String email);
}
