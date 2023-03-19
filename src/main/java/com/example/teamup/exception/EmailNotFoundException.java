package com.example.teamup.exception;

public class EmailNotFoundException extends RuntimeException{
    private String debugMessage;

    public EmailNotFoundException(String message) {

        super(message);
    }

    public EmailNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
