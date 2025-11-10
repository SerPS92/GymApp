package com.gymapp.shared.error.exception;

import com.gymapp.shared.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message, cause);
    }
}
