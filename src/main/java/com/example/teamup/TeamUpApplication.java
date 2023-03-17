package com.example.teamup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

//@RestController
//@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
@SpringBootApplication
public class TeamUpApplication {
    @GetMapping("/hello")
    public String world(){
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(TeamUpApplication.class, args);
    }

}
