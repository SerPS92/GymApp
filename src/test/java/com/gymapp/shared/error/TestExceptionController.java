package com.gymapp.shared.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/test-exceptions")
class TestExceptionController {

    enum DummyEnum { ONE, TWO }

    @GetMapping("/app")
    public void throwAppException() {
        throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, "Custom app exception");
    }

    @GetMapping("/method-arg-not-valid")
    public void throwMethodArgumentNotValid() throws MethodArgumentNotValidException {
        throw new MethodArgumentNotValidException(null, null);
    }

    @GetMapping("/bind")
    public void throwBindException() throws org.springframework.validation.BindException {
        throw new org.springframework.validation.BindException(new Object(), "dummy");
    }

    @GetMapping("/illegal-arg")
    public void throwIllegalArgument() {
        throw new IllegalArgumentException("Invalid argument");
    }

    @GetMapping("/constraint")
    public void throwConstraintViolation() {
        throw new ConstraintViolationException("Validation failed", Set.of());
    }

    @GetMapping("/response-status")
    public void throwResponseStatus() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
    }

    @GetMapping("/conflict")
    public void throwConflict() {
        throw new ConflictException("Duplicate record");
    }

    @GetMapping("/data-integrity")
    public void throwDataIntegrity() {
        throw new DataIntegrityViolationException("Unique constraint violated");
    }

    @GetMapping("/http-message")
    public void throwHttpMessageNotReadable() {
        throw new HttpMessageNotReadableException("Malformed JSON body");
    }

    @GetMapping("/invalid-enum")
    public void throwInvalidEnum() {
        throw new HttpMessageNotReadableException(
                "Enum invalid",
                InvalidFormatException.from(null, "Invalid value", "WRONG", DummyEnum.class)
        );
    }


    @GetMapping("/type-mismatch")
    public void throwTypeMismatch() {
        throw new MethodArgumentTypeMismatchException("value", Integer.class, "id", null, new IllegalArgumentException());
    }

    @GetMapping("/unexpected")
    public void throwUnexpected() {
        throw new RuntimeException("Unexpected error");
    }
}
