package com.gymapp.infrastructure.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GymApp API")
                        .description("API documentation for GymApp backend.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sergio")
                                .url("https://github.com/SerPS92/GymApp")
                                .email("contact@gymapp.com")));
    }
}
