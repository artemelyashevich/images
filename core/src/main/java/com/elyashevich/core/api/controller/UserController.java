package com.elyashevich.core.api.controller;

import com.elyashevich.core.api.dto.user.UserDto;
import com.elyashevich.core.api.mapper.UserMapper;
import com.elyashevich.core.domain.entity.User;
import com.elyashevich.core.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        var users = this.userService.findAll();

        var dtos = this.userMapper.toDto(users);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(final @PathVariable("id") String id) {
        var user = this.userService.findById(id);
        var dto = this.userMapper.toDto(user);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> findByCurrentUser(final @RequestAttribute("email") String email) {
        var user = this.userService.findByEmail(email);
        var dto = this.userMapper.toDto(user);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(final @PathVariable("id") String id, final @Valid @RequestBody UserDto userDto) {
        var candidate = this.userMapper.toEntity(userDto);
        var user = this.userService.update(id, candidate);
        var dto = this.userMapper.toDto(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(final @PathVariable("id") String id) {
        this.userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
