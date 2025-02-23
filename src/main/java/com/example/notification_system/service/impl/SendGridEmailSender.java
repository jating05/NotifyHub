package com.example.notification_system.service.impl;

import com.example.notification_system.configuration.SendgridConfig;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Service
//public class SendGridEmailSender {
//
//    @Autowired
//    SendgridConfig sendgridconfig;
//
//    public void sendEmail(String toEmail, String subject, String message) throws IOException {
//        Email from = new Email(sendgridconfig.getFromEmail(), sendgridconfig.getFromName());
//        Email to = new Email(toEmail);
//        Content content = new Content("text/plain", message);
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(sendgridconfig.getApiKey());
//        Request request = new Request();
//
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//
//            Response response = sg.api(request);
//
//            int statusCode = response.getStatusCode();
//            if (statusCode != 200 && statusCode != 202) {
//                throw new IOException("Failed to send email: " + response.getBody());
//            }
//        } catch (IOException ex) {
//            // Handle the exception as needed in your application
//            throw ex;
//        }
//    }
//}

@Service
public class SendGridEmailSender {

    private final WebClient webClient;
    private final SendgridConfig sendgridConfig;

    @Autowired
    public SendGridEmailSender(SendgridConfig sendgridConfig) {
        this.sendgridConfig = sendgridConfig;
        this.webClient = WebClient.builder()
                .baseUrl(sendgridConfig.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + sendgridConfig.getApiKey())
                .build();
    }

    public void sendEmail(String toEmail, String subject, String message) {
        String url = "/mail/send";

        Map<String, Object> emailPayload = new HashMap<>();
        Map<String, String> from = new HashMap<>();
        from.put("email", sendgridConfig.getFromEmail());
        from.put("name", sendgridConfig.getFromName());

        Map<String, String> to = new HashMap<>();
        to.put("email", toEmail);

        Map<String, String> content = new HashMap<>();
        content.put("type", "text/plain");
        content.put("value", message);

        emailPayload.put("personalizations", new Object[] {
                Map.of("to", new Object[] { to }, "subject", subject)
        });
        emailPayload.put("from", from);
        emailPayload.put("content", new Object[] { content });

        Mono<Void> response = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(emailPayload)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> {
                    // Log error as needed
                    return Mono.error(new RuntimeException("Failed to send Email via SendGrid", e));
                });

        response.block(); // Blocking for simplicity; consider making this asynchronous
    }
}