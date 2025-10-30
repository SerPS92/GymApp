package com.gymapp.shared.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GlobalExceptionHandlerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("AppException -> BAD_REQUEST con código BAD_REQUEST")
    void handleAppException() throws Exception {
        mockMvc.perform(get("/test-exceptions/app"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.detail").value("Custom app exception"));
    }

    @Test
    @DisplayName("IllegalArgumentException -> BAD_REQUEST con mensaje en errors")
    void handleIllegalArgument() throws Exception {
        mockMvc.perform(get("/test-exceptions/illegal-arg"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.errors[0].message").value("Invalid argument"));
    }

    @Test
    @DisplayName("ConstraintViolationException -> BAD_REQUEST con code VALIDATION_ERROR")
    void handleConstraintViolation() throws Exception {
        mockMvc.perform(get("/test-exceptions/constraint"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("ResponseStatusException -> NOT_FOUND con código NOT_FOUND")
    void handleResponseStatusException() throws Exception {
        mockMvc.perform(get("/test-exceptions/response-status"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.detail").value("Entity not found"));
    }

    @Test
    @DisplayName("ConflictException -> CONFLICT con código CONFLICT")
    void handleConflictException() throws Exception {
        mockMvc.perform(get("/test-exceptions/conflict"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Conflict"))
                .andExpect(jsonPath("$.detail").value("Duplicate record"));
    }

    @Test
    @DisplayName("DataIntegrityViolationException -> CONFLICT con mensaje genérico")
    void handleDataIntegrityViolation() throws Exception {
        mockMvc.perform(get("/test-exceptions/data-integrity"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflict"))
                .andExpect(jsonPath("$.detail").value("Data integrity violation"));
    }

    @Test
    @DisplayName("HttpMessageNotReadableException -> BAD_REQUEST con cuerpo problem+json")
    void handleHttpMessageNotReadable() throws Exception {
        mockMvc.perform(get("/test-exceptions/http-message"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Invalid request body"));
    }

    @Test
    @DisplayName("Invalid enum format -> BAD_REQUEST con título INVALID_ENUM_VALUE")
    void handleInvalidEnumFormat() throws Exception {
        mockMvc.perform(get("/test-exceptions/invalid-enum"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid enum value"));
    }

    @Test
    @DisplayName("MethodArgumentTypeMismatchException -> BAD_REQUEST con errors[0].field y rejectedValue")
    void handleTypeMismatch() throws Exception {
        mockMvc.perform(get("/test-exceptions/type-mismatch"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("id"))
                .andExpect(jsonPath("$.errors[0].message").value("Invalid value for parameter"));
    }

    @Test
    @DisplayName("Unexpected exception -> INTERNAL_SERVER_ERROR con code INTERNAL_ERROR")
    void handleUnexpected() throws Exception {
        mockMvc.perform(get("/test-exceptions/unexpected"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.title").value("Internal Server Error"));
    }
}
