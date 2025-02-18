package com.elyashevich.core.domain;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}