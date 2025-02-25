package com.elyashevich.core.api.dto.auth;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenDto(
        @NotNull(message = "Refresh token must be not null")
        String token
) {
}