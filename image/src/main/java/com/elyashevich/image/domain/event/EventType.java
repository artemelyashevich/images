package com.elyashevich.image.domain.event;

public enum EventType {
    PENDING("PENDING"),
    FULFILLED("FULFILLED"),
    REJECTED("REJECTED");

    EventType(final String name) {}
}
