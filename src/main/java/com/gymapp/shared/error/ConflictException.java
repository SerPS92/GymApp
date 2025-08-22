package com.gymapp.shared.error;

import org.springframework.http.HttpStatus;

public class ConflictException extends AppException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.CONFLICT, message);
    }
    public ConflictException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, ErrorCode.CONFLICT, message, cause);
    }
}
