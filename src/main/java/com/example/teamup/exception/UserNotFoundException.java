package com.example.teamup.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotFoundException extends RuntimeException{
    private String debugMessage;

    public UserNotFoundException(String message) {

        super(message);
    }

    public UserNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
