package com.elyashevich.core.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record RegisterDto(

        @NotNull(message = "First name must be not null")
        @NotEmpty(message = "First name must be not empty")
        @Length(min=2, max=255, message = "First name must be in {min} and {max}")
        String firstName,

        @NotNull(message = "Last name must be not null")
        @NotEmpty(message = "Last name must be not empty")
        @Length(min=2, max=255, message = "Last name must be in {min} and {max}")
        String lastName,

        @NotNull(message = "Email must be not null")
        @NotEmpty(message = "Email must be not empty")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Password must be not null")
        @NotEmpty(message = "Password must be not empty")
        @Length(min = 8, max = 255, message = "Password must be in {min} and {max}")
        String password,

        @NotNull(message = "BirthDate must be not null")
        @Past(message = "Birth date must be in the past")
        LocalDateTime birthDate
) {
}