package com.gymapp.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public com.fasterxml.jackson.databind.Module jsonNullableModule() {
        return new org.openapitools.jackson.nullable.JsonNullableModule();
    }
}

