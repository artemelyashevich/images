package com.elyashevich.core.service.impl;

import com.elyashevich.core.domain.JwtResponse;
import com.elyashevich.core.domain.entity.User;
import com.elyashevich.core.exception.PasswordMismatchException;
import com.elyashevich.core.service.AuthService;
import com.elyashevich.core.service.UserService;
import com.elyashevich.core.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtResponse login(final User user) {
        log.debug("Login attempt");

        var candidate = this.userService.loadUserByUsername(user.getEmail());
        if (!this.passwordEncoder.matches(user.getPassword(), candidate.getPassword())) {
            log.warn("Incorrect password");
            throw new PasswordMismatchException("Password mismatch");
        }

        var response = this.authenticate(candidate);

        log.info("User logged in successfully");
        return response;
    }

    @Override
    public JwtResponse register(final User user) {
        return null;
    }

    @Override
    public JwtResponse refresh(final String token) {
        return null;
    }

    private JwtResponse authenticate(final UserDetails user) {
        var accessToken = TokenUtil.generateToken(user, 1000 * 60 * 30);
        var refreshToken = TokenUtil.generateToken(user, 1000 * 60 * 60 * 24 * 10);
        return new JwtResponse(accessToken, refreshToken);
    }
}
