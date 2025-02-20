package com.elyashevich.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaConstantUtil {

    public static final String DESTINATION = "/topic/notifications";
    public static final String GROUP_ID = "notification-group";
}
