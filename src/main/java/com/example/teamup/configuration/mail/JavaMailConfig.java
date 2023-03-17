package com.example.teamup.configuration.mail;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//@Configuration
public class JavaMailConfig {
//    @Bean
//    public JavaMailSender getJavaMailSender(){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
////        mailSender.setUsername("teamupforsports@gmail.com");
////        mailSender.setPassword("Random.1990");
//        mailSender.setUsername("funitureoakland@gmail.com");
//        mailSender.setPassword("qmthrwjtswvdmaqw");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtps");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
}
