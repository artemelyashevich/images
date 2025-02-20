package com.elyashevich.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenConstantUtil {

    public static final Long ACCESS_TOKEN_EXPIRES_TIME = 1800000L; // 30m
    public static final Long REFRESH_TOKEN_EXPIRES_TIME = 864000000L; // 10d
    public static final String ROLES = "roles";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
}
