package com.elyashevich.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.elyashevich.core.util.SwaggerConstantUtil.*;

@Configuration
public class SwaggerConfig {

    @Value("${application.open-api.email:example}")
    private String email;

    @Value("${application.open-api.server:http://localhost:8222}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url(this.serverUrl)
                        )
                )
                .info(
                        new Info()
                                .title(AUTH_SERVICE_TITLE)
                                .description(AUTH_SERVICE_DESCRIPTION)
                                .version(AUTH_SERVICE_VERSION)
                                .contact(new Contact().email(this.email))
                );
    }
}