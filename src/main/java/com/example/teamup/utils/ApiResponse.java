package com.example.teamup.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;

    private  Boolean status;

    private T data;

    private HttpStatus httpStatus;

    public ApiResponse(String message, Boolean status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ApiResponse(String message, T data, HttpStatus httpStatus) {
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }
}
