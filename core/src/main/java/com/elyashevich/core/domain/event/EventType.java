package com.elyashevich.core.domain.event;

public enum EventType {
    PENDING("PENDING"),
    FULFILLED("FULFILLED"),
    REJECTED("REJECTED");

    EventType(final String name) {}
}
