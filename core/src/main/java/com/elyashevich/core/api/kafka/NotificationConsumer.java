package com.elyashevich.core.api.kafka;

import com.elyashevich.core.domain.event.NotificationEvent;
import com.elyashevich.core.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeOrderEvent(final String message) throws JsonProcessingException {

        var objectMapper = new ObjectMapper();
        var event = objectMapper.readValue(message, NotificationEvent.class);

        log.debug("Consuming notification event: {}", event);

        this.notificationService.sendNotification(event);

        log.info("Consumed notification event: {}", event);
    }
}