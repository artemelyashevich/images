package com.elyashevich.image.kafka;

import com.elyashevich.image.domain.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendOrderEvent(final NotificationEvent event) {
        log.debug("Sending notification event: {}", event);

        kafkaTemplate.send("notification-topic", event);

        log.info("Notification event sent {}", event);
    }
}
