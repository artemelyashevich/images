package com.elyashevich.core.domain.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NotificationEvent {
    private EventType eventType;
    private String content;
}
