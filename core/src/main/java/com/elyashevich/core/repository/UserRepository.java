package com.elyashevich.core.repository;

import com.elyashevich.core.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
