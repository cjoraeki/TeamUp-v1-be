package com.example.teamup.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationException extends RuntimeException{

    private String debugMessage;

    public ValidationException(String message) {

        super(message);
    }

    public ValidationException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
