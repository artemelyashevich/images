package com.elyashevich.core.api.controller;

import com.elyashevich.core.api.dto.auth.LoginDto;
import com.elyashevich.core.api.dto.auth.RefreshTokenDto;
import com.elyashevich.core.api.dto.auth.RegisterDto;
import com.elyashevich.core.api.dto.auth.ResetPasswordDto;
import com.elyashevich.core.api.mapper.UserMapper;
import com.elyashevich.core.domain.response.JwtResponse;
import com.elyashevich.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            final @Valid @RequestBody LoginDto loginDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
        var user = this.userMapper.toEntity(loginDto);
        var response = this.authService.login(user);

        return ResponseEntity
                .created(
                        uriComponentsBuilder.path("/api/v1/users/{id}")
                                .build(user.getId())
                )
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
            final @Valid @RequestBody RegisterDto registerDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
        var user = this.userMapper.toEntity(registerDto);
        var response = this.authService.register(user);

        return ResponseEntity
                .created(
                        uriComponentsBuilder.path("/api/v1/users/{id}")
                                .build(user.getId())
                )
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(final @Valid @RequestBody RefreshTokenDto dto) {
        var response = this.authService.refresh(dto.token());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/reset-password")
    @PreAuthorize("#email == authentication.principal")
    public ResponseEntity<Void> resetPassword(
            final @Valid @RequestBody ResetPasswordDto dto,
            final @RequestAttribute("email") String email
    ) {
        this.authService.resetPassword(dto, email);
        return ResponseEntity.accepted().build();
    }
}
