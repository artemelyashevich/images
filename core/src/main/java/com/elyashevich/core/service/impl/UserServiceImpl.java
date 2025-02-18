package com.elyashevich.core.service.impl;

import com.elyashevich.core.domain.User;
import com.elyashevich.core.exception.ResourceNotFoundException;
import com.elyashevich.core.repository.UserRepository;
import com.elyashevich.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_WITH_ID_WAS_NOT_FOUND_TEMPLATE = "User with id '%s' was not found";
    public static final String USER_WITH_EMAIL_WAS_NOT_FOUND_TEMPLATE = "User with email '%s' was not found";
    private final UserRepository userRepository;

    @Override
    public User create(final User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        log.debug("Attempting to find all users");

        var users = this.userRepository.findAll();

        log.info("Found {} users", users.size());
        return users;
    }

    @Override
    public User findById(final String id) {
        log.debug("Attempting to find user with id {}", id);

        var user = this.userRepository.findById(id).orElseThrow(
                ()-> {
                    var message = USER_WITH_ID_WAS_NOT_FOUND_TEMPLATE.formatted(id);
                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found user with id {}", user.getId());
        return user;
    }

    @Override
    public User findByEmail(final String email) {
        log.debug("Attempting to find user with email {}", email);

        var user = this.userRepository.findByEmail(email).orElseThrow(
                ()-> {
                    var message = USER_WITH_EMAIL_WAS_NOT_FOUND_TEMPLATE.formatted(email);
                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found user with email {}", user.getEmail());
        return user;
    }

    @Override
    public User update(final String id, final User user) {
        return null;
    }

    @Transactional
    @Override
    public void delete(final String id) {
        log.debug("Attempting to delete user with id {}", id);

        var user = this.findById(id);
        this.userRepository.delete(user);

        log.info("Deleted user with id {}", user.getId());
    }

    @Override
    public void resetPassword(final String id, final String password) {

    }
}
