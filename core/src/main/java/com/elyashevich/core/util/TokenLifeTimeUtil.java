package com.elyashevich.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenLifeTimeUtil {

    public static final Long ACCESS_TOKEN_EXPIRES_TIME = 1800000L; // 30m
    public static final Long REFRESH_TOKEN_EXPIRES_TIME = 864000000L; // 10d
}
