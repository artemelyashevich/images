package com.elyashevich.image.repository;

import com.elyashevich.image.domain.entity.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {
}
