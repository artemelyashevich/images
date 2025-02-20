package com.elyashevich.image.repository;

import com.elyashevich.image.domain.entity.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {

    List<ImageMetadata> findByUserId(final String userId);
}
