package com.f1.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Formula 1 API")
                        .version("1.0.0")
                        .description("RESTful API for managing Formula 1 drivers and teams information for the 2025 season. " +
                                "This API follows REST best practices with proper versioning and error handling.")
                        .contact(new Contact()
                                .name("F1 API Support")
                                .email("support@f1api.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server")
                ));
    }
}
