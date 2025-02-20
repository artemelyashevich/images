package com.elyashevich.core.service.impl;

import com.elyashevich.core.domain.event.NotificationEvent;
import com.elyashevich.core.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.elyashevich.core.util.KafkaConstantUtil.DESTINATION;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(final NotificationEvent event) {
        this.messagingTemplate.convertAndSend(DESTINATION, event);
    }
}
