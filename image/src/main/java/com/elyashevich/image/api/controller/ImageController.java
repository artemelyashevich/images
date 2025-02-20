package com.elyashevich.image.api.controller;

import com.elyashevich.image.domain.entity.ImageMetadata;
import com.elyashevich.image.service.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<GridFSFile>> findAll() {
        var images = new ArrayList<GridFSFile>();
        var data = this.imageService.findAll();
        data.forEach(images::add);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GridFSFile> findById(@PathVariable("id") String id) {
        var image = this.imageService.findById(id);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GridFSFile>> findAllByUserId(@PathVariable("userId") String userId) {
        var data = this.imageService.findAllByUserId(userId);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ImageMetadata> create(
            final @PathVariable("userId") String userId,
            final @RequestParam("file") MultipartFile file,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
        var metadata = this.imageService.create(file, userId);
        return ResponseEntity
                .created(
                        uriComponentsBuilder.path("/api/v1/images/{id}")
                                .build(metadata.getId())
                )
                .body(metadata);
    }
}
