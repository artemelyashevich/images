package com.elyashevich.core.api.mapper;

import com.elyashevich.core.api.dto.auth.LoginDto;
import com.elyashevich.core.api.dto.auth.RegisterDto;
import com.elyashevich.core.api.dto.user.UserDto;
import com.elyashevich.core.domain.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(LoginDto dto);

    User toEntity(RegisterDto dto);

    User toEntity(UserDto dto);

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);
}
