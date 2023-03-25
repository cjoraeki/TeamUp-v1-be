package com.example.teamup.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputException extends RuntimeException{
    private String debugMessage;

    public InvalidInputException(String message) {

        super(message);
    }

    public InvalidInputException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
