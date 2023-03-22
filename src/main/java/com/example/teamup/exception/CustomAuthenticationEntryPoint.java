package com.example.teamup.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomAuthenticationEntryPoint extends RuntimeException{

    private String debugMessage;

    public CustomAuthenticationEntryPoint(String message) {

        super(message);
    }

    public CustomAuthenticationEntryPoint(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
