package com.example.teamup.exception;

public class InvalidTokenException extends RuntimeException{
    private String debugMessage;

    public InvalidTokenException(String message) {

        super(message);
    }

    public InvalidTokenException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
