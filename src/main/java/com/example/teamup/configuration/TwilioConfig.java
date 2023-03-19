package com.example.teamup.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Setter
@Getter
@NoArgsConstructor
@Configuration
@ConfigurationProperties("twilio")
public class TwilioConfig {

        private String accountSid;
        private String authToken;
        private String trialNumber;
}
