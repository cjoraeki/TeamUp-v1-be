package com.example.teamup.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class ErrorResponse{
    private String message;
    private String debugMessage;
    private HttpStatus status;
}
