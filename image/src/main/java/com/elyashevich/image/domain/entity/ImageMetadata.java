package com.elyashevich.image.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "imageMetadata")
public class ImageMetadata {

    @Id
    private String id;

    private String filename;

    private String contentType;

    private long size;

    private String userId;

    private String uploadDate;
}
