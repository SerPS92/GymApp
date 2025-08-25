package com.gymapp.shared.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorConstants {


    public static final String URN_PROBLEM_VALIDATION_ERROR = "urn:problem:validation_error";
    public static final String URN_PROBLEM_BAD_REQUEST = "urn:problem:bad_request";
    public static final String URN_PROBLEM_INTERNAL_ERROR = "urn:problem:internal_error";
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String INVALID_REQUEST_PARAMETERS = "Invalid request parameters";
    public static final String UNEXPECTED_ERROR = "Unexpected error";
    public static final String CODE = "code";
    public static final String PATH = "path";
    public static final String URN_PROBLEM = "urn:problem:";
    public static final String FIELD = "field";
    public static final String MESSAGE = "message";
    public static final String REJECTED_VALUE = "rejectedValue";
    public static final String ERRORS = "errors";
}
