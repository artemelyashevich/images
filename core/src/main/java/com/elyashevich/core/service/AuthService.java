package com.elyashevich.core.service;

import com.elyashevich.core.domain.JwtResponse;
import com.elyashevich.core.domain.entity.User;

public interface AuthService {

    JwtResponse login(final User user);

    JwtResponse register(final User user);

    JwtResponse refresh(final String token);
}
