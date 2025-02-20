package com.elyashevich.core.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CorsConstantUtil {

    public static final List<String> ALLOWS_ORIGINS = List.of("http://localhost:5173", "http://localhost:4200");
    public static final List<String> ALLOWS_METHODS = List.of("GET", "POST", "PUT", "DELETE");
    // FIXME
    public static final List<String> ALLOWS_HEADERS = List.of("*");
}

