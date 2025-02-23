package com.example.notification_system.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//old config
//@Component
//@ConfigurationProperties(prefix = "twilio")
//@Data
//public class TwilioConfig {
//    private String accountSid;
//    private String authToken;
//    private String trialNumber;
//}


@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {
    private String baseUrl;
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
