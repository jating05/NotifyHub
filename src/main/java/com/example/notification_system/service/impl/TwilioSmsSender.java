package com.example.notification_system.service.impl;

import com.example.notification_system.configuration.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

//@Service
//public class TwilioSmsSender {
//
//    @Autowired
//    TwilioConfig twilioConfig;
//
//    @PostConstruct
//    public void init() {
//        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
//    }
//
//    public void sendSms(String to, String message) {
//        System.out.println("Sending SMS to " + to + " with message: " + message);
//        Message.creator(
//                new com.twilio.type.PhoneNumber(to),
//                new com.twilio.type.PhoneNumber(twilioConfig.getTrialNumber()),
//                message
//        ).create();
//    }
//}

@Service
public class TwilioSmsSender {

    private final WebClient webClient;
    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        this.webClient = WebClient.builder()
                .baseUrl(twilioConfig.getBaseUrl())
                .build();
    }

    public void sendSms(String to, String message) {
        String url = String.format("/Accounts/%s/Messages.json", twilioConfig.getAccountSid());

        String auth = twilioConfig.getAccountSid() + ":" + twilioConfig.getAuthToken();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        Mono<Void> response = webClient.post()
                .uri(url)
                .header("Authorization", "Basic " + encodedAuth)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("To=" + to +
                        "&From=" + twilioConfig.getTrialNumber() +
                        "&Body=" + message)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> {
                    // Log error as needed
                    return Mono.error(new RuntimeException("Failed to send SMS via Twilio", e));
                });

        response.block(); // Blocking for simplicity; consider making this asynchronous
    }
}

