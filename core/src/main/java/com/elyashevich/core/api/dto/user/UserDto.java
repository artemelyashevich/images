package com.elyashevich.core.api.dto.user;

import java.sql.Date;
import java.util.UUID;

public record UserDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Date birthDate
) {
}