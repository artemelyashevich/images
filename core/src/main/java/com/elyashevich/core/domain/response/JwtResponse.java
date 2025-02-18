package com.elyashevich.core.domain.response;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}