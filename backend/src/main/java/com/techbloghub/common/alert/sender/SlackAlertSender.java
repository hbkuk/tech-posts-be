package com.techbloghub.common.alert.sender;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackAlertSender implements AlertSender {

    private static final String REQUEST_URI = "https://hooks.slack.com/services";

    @Override
    public void send(String message, String hookUri) {
        WebClient.create(REQUEST_URI)
            .post()
            .uri(hookUri)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new MessageRequest(message))
            .retrieve()
            .bodyToMono(Void.class)
            .subscribe();
    }
}
