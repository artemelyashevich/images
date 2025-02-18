package com.elyashevich.core.service;

import com.elyashevich.core.domain.event.NotificationEvent;

public interface NotificationService {

    void sendNotification(final NotificationEvent event);
}
