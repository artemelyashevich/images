package com.elyashevich.core.service.impl;

import com.elyashevich.core.api.dto.auth.ResetPasswordDto;
import com.elyashevich.core.domain.entity.Role;
import com.elyashevich.core.domain.entity.User;
import com.elyashevich.core.exception.ResourceAlreadyExistsException;
import com.elyashevich.core.exception.ResourceNotFoundException;
import com.elyashevich.core.repository.UserRepository;
import com.elyashevich.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.elyashevich.core.util.UserTemplateMessageUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User create(final User user) {
        log.debug("Attempting create new user: '{}'", user.getEmail());

        if (this.userRepository.existsByEmail(user.getEmail())) {
            var message = String.format(USER_WITH_EMAIL_ALREADY_EXISTS_TEMPLATE, user.getEmail());

            log.warn(message);

            throw new ResourceAlreadyExistsException(message);
        }

        var newUser = this.userRepository.save(user);

        log.info("User with email '{}' has been created", user.getEmail());
        return newUser;
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

    @Transactional
    @Override
    public User update(final String id, final User user) {
        log.debug("Attempting to update user with id {}", id);

        var oldUser = this.findById(id);
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setBirthday(user.getBirthday());

        var newUser = this.userRepository.save(oldUser);

        log.info("User with email '{}' has been updated", user.getEmail());
        return newUser;
    }

    @Transactional
    @Override
    public void delete(final String id) {
        log.debug("Attempting to delete user with id {}", id);

        var user = this.findById(id);
        this.userRepository.delete(user);

        log.info("Deleted user with id {}", user.getId());
    }

    @Transactional
    @Override
    public void resetPassword(final String id, final String newPassword) {
        log.debug("Attempting to reset password for user with id {}", id);

        var oldUser = this.findById(id);

        oldUser.setPassword(newPassword);
        var newUser = this.userRepository.save(oldUser);

        log.info("Reset password for user with id {}", newUser.getId());
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        var user = this.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(Role::name)
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }

}
