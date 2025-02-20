package com.elyashevich.core.service;

import com.elyashevich.core.domain.entity.User;

import java.util.List;

public interface UserService {
    User create(final User user);

    List<User> findAll();

    User findById(final String id);

    User findByEmail(final String email);

    User update(final String id, final User user);

    void delete(final String id);

    void resetPassword(final String id, final String newPassword);
}
