package com.elyashevich.core.service;

import com.elyashevich.core.api.dto.auth.ResetPasswordDto;
import com.elyashevich.core.domain.JwtResponse;
import com.elyashevich.core.domain.entity.User;

public interface AuthService {

    JwtResponse login(final User user);

    JwtResponse register(final User user);

    JwtResponse refresh(final String token);

    void resetPassword(final ResetPasswordDto resetPasswordDto, final String email);
}
