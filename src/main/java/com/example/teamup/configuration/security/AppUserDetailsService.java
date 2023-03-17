package com.example.teamup.configuration.security;

import com.example.teamup.entity.User;
import com.example.teamup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@RequiredArgsConstructor
@Service
@JsonComponent
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
        User user = null;
        if (emailOrPhone.matches("[0-9]{11}")){
            user = userRepository.findByPhoneNumber(emailOrPhone)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User phone number: " + emailOrPhone + "not found"));

        }else {
            user = userRepository.findByEmail(emailOrPhone)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User email: " + emailOrPhone + "not found"));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
