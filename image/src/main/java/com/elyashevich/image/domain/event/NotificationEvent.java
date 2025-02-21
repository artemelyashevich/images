package com.elyashevich.image.domain.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationEvent {

    private EventType eventType;

    private String content;
}