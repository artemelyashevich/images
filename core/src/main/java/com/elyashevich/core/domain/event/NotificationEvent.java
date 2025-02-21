package com.elyashevich.core.domain.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class NotificationEvent {
    private EventType eventType;
    private String content;
}
