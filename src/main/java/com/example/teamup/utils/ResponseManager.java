package com.example.teamup.utils;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResponseManager<T> {

    public ApiResponse<T> success(T data){

        return new ApiResponse<>("Request Successful", true, data);

    }
}
