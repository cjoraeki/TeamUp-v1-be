package com.example.teamup.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlreadyExistsException extends RuntimeException{
    private String debugMessage;

    public AlreadyExistsException(String message) {

        super(message);
    }

    public AlreadyExistsException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

}
