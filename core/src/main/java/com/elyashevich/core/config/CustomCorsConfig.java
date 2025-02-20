package com.elyashevich.core.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.elyashevich.core.util.CorsConstantUtil.*;

@Component
public class CustomCorsConfig implements CorsConfigurationSource {

    @Override
    public CorsConfiguration getCorsConfiguration(final @NonNull HttpServletRequest request) {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(ALLOWS_ORIGINS);
        config.setAllowedMethods(ALLOWS_METHODS);
        config.setAllowedHeaders(ALLOWS_HEADERS);
        config.setAllowCredentials(true);
        return config;
    }
}