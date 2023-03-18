package com.example.teamup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "*")
@SpringBootApplication
public class TeamUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamUpApplication.class, args);
    }

}
