package com.elyashevich.core.repository;

import com.elyashevich.core.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(final String email);

    boolean existsByEmail(final String email);
}
