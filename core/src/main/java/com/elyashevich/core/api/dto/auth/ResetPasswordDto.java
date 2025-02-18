package com.elyashevich.core.api.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ResetPasswordDto(

        @NotNull(message = "OldPassword must be not null")
        @NotEmpty(message = "OldPassword must be not empty")
        @Length(min = 8, max = 255, message = "OldPassword must be in {min} and {max}")
        String oldPassword,

        @NotNull(message = "NewPassword must be not null")
        @NotEmpty(message = "NewPassword must be not empty")
        @Length(min = 8, max = 255, message = "NewPassword must be in {min} and {max}")
        String newPassword
) {
}