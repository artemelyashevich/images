package com.elyashevich.core.service.impl;

import com.elyashevich.core.api.dto.auth.ResetPasswordDto;
import com.elyashevich.core.domain.JwtResponse;
import com.elyashevich.core.domain.entity.Role;
import com.elyashevich.core.domain.entity.User;
import com.elyashevich.core.exception.PasswordMismatchException;
import com.elyashevich.core.service.AuthService;
import com.elyashevich.core.service.RefreshTokenService;
import com.elyashevich.core.service.UserService;
import com.elyashevich.core.util.TokenLifeTimeUtil;
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
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtResponse login(final User user) {
        log.debug("Attempting to login user: {}", user.getEmail());

        var candidate = this.userService.loadUserByUsername(user.getEmail());
        if (!this.passwordEncoder.matches(user.getPassword(), candidate.getPassword())) {
            log.warn("Incorrect password");
            throw new PasswordMismatchException("Password mismatch");
        }

        var response = this.authenticate(candidate);
        this.refreshTokenService.create(response.refreshToken(), this.userService.findByEmail(user.getEmail()));
        log.info("User logged in successfully");
        return response;
    }

    @Transactional
    @Override
    public JwtResponse register(final User user) {
        log.debug("Register attempt");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(Role.ROLE_USER);
        var candidate = this.userService.create(user);

        var response = this.authenticate(this.userService.loadUserByUsername(candidate.getEmail()));
        this.refreshTokenService.create(response.refreshToken(), candidate);
        log.info("User registered successfully");
        return response;
    }

    @Override
    public JwtResponse refresh(final String token) {
        return null;
    }

    @Transactional
    @Override
    public void resetPassword(final ResetPasswordDto resetPasswordDto, final String email) {
        var oldUser = this.userService.findByEmail(email);
        if (!this.passwordEncoder.matches(resetPasswordDto.oldPassword(), oldUser.getPassword())) {
            log.warn("Incorrect password");
            throw new PasswordMismatchException("Password mismatch");
        }
        this.userService.resetPassword(oldUser.getId(), this.passwordEncoder.encode(resetPasswordDto.newPassword()));
    }

    private JwtResponse authenticate(final UserDetails user) {
        var accessToken = TokenUtil.generateToken(user, TokenLifeTimeUtil.ACCESS_TOKEN_EXPIRES_TIME);
        var refreshToken = TokenUtil.generateToken(user, TokenLifeTimeUtil.REFRESH_TOKEN_EXPIRES_TIME);
        return new JwtResponse(accessToken, refreshToken);
    }
}
