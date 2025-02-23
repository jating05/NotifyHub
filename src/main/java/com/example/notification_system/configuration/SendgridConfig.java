package com.example.notification_system.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Component
//@ConfigurationProperties(prefix = "sendgrid")
//@Data
//public class SendgridConfig {
//    private String apiKey;
//    private String fromEmail;
//    private String fromName;
//
//}

@Configuration
@ConfigurationProperties(prefix = "sendgrid")
@Data
public class SendgridConfig {
    private String baseUrl;
    private String apiKey;
    private String fromEmail;
    private String fromName;
}

