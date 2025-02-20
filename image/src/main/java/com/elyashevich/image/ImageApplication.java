package com.elyashevich.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageApplication.class, args);
	}

}
