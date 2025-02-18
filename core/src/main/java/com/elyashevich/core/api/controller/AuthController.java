package com.elyashevich.core.api.controller;

import com.elyashevich.core.api.dto.auth.LoginDto;
import com.elyashevich.core.api.dto.auth.RefreshTokenDto;
import com.elyashevich.core.api.dto.auth.RegisterDto;
import com.elyashevich.core.api.dto.auth.ResetPasswordDto;
import com.elyashevich.core.domain.response.JwtResponse;
import com.elyashevich.core.domain.entity.User;
import com.elyashevich.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(final @Valid @RequestBody LoginDto loginDto) {
        var user = this.modelMapper.map(loginDto, User.class);
        var response = this.authService.login(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(final @Valid @RequestBody RegisterDto registerDto) {
        var user = this.modelMapper.map(registerDto, User.class);
        var response = this.authService.register(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
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
