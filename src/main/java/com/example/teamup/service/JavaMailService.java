package com.example.teamup.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface JavaMailService {
    ResponseEntity<String> sendMail(String receiverEmail, String subject, String emailBody) throws IOException;
    ResponseEntity<String> sendMailAlt(String receiverEmail, String subject, String emailBody) throws IOException;
}
