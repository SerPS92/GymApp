package com.gymapp.shared.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message);
    }
    public BadRequestException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, message, cause);
    }
}
