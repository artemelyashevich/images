package com.elyashevich.image.service.impl;

import com.elyashevich.image.domain.entity.ImageMetadata;
import com.elyashevich.image.domain.event.EventType;
import com.elyashevich.image.domain.event.NotificationEvent;
import com.elyashevich.image.exception.UploadingException;
import com.elyashevich.image.kafka.NotificationProducer;
import com.elyashevich.image.repository.ImageMetadataRepository;
import com.elyashevich.image.service.ImageService;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final GridFsTemplate gridFsTemplate;
    private final ImageMetadataRepository imageMetadataRepository;
    private final NotificationProducer notificationProducer;

    @Override
    public GridFSFindIterable findAll() {
        return this.gridFsTemplate.find(null);
    }

    @Transactional
    @Override
    public List<GridFSFile> findAllByUserId(final String userId) {
        var imageMetadata = imageMetadataRepository.findByUserId(userId);
        var data = new ArrayList<GridFSFile>();
        imageMetadata.forEach(image -> {
            data.add(this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(image.getId()))));
        });
        return data;
    }

    @Override
    public GridFSFile findById(final String id) {
        return gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
    }

    @Transactional
    @Override
    public ImageMetadata create(final MultipartFile file, final String userId) {
        try {
            notificationProducer.sendOrderEvent(NotificationEvent.builder()
                            .eventType(EventType.PENDING)
                    .build());

            var id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            var image = ImageMetadata.builder()
                    .id(id.toString())
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .userId(userId)
                    .uploadDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .build();

            var result = this.imageMetadataRepository.save(image);

            notificationProducer.sendOrderEvent(NotificationEvent.builder()
                    .eventType(EventType.FULFILLED)
                    .build());

            return result;
        } catch (IOException e) {
            notificationProducer.sendOrderEvent(NotificationEvent.builder()
                    .eventType(EventType.REJECTED)
                    .build());
            throw new UploadingException("Error uploading file", e);
        }
    }
}
