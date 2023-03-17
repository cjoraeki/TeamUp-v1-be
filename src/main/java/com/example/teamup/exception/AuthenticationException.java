package com.example.teamup.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationException extends RuntimeException{
    private String debugMessage;

    public AuthenticationException(String message) {

        super(message);
    }

    public AuthenticationException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
