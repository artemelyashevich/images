package com.elyashevich.core.service;

import com.elyashevich.core.domain.entity.User;

public interface RefreshTokenService {

    String create(final String token, final User user);
}
