package com.elyashevich.image.service;

import com.elyashevich.image.domain.entity.ImageMetadata;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    GridFSFindIterable findAll();

    GridFSFile findById(final String id);

    ImageMetadata create(final MultipartFile file, final String userId);
}
