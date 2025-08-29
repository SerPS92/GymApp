package com.gymapp.shared.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalExceptionHandlerConstants {


    public static final String URN_PROBLEM_VALIDATION_ERROR = "urn:problem:validation_error";
    public static final String URN_PROBLEM_BAD_REQUEST = "urn:problem:bad_request";
    public static final String URN_PROBLEM_INTERNAL_ERROR = "urn:problem:internal_error";
    public static final String URN_PROBLEM_CONFLICT = "urn:problem:conflict";
    public static final String URN_PROBLEM_INVALID_ENUM = "urn:problem:invalid_enum";
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String INVALID_REQUEST_PARAMETERS = "Invalid request parameters";
    public static final String DATA_INTEGRITY_VIOLATION = "Data integrity violation.";
    public static final String UNEXPECTED_ERROR = "Unexpected error";
    public static final String CONFLICT = "CONFLICT";
    public static final String CODE = "code";
    public static final String PATH = "path";
    public static final String URN_PROBLEM = "urn:problem:";
    public static final String FIELD = "field";
    public static final String MESSAGE = "message";
    public static final String REJECTED_VALUE = "rejectedValue";
    public static final String ERRORS = "errors";
    public static final String ERROR = "Error";
    public static final String NOT_FOUND = "not_found";
    public static final String INVALID_ENUM_VALUE = "Invalid enum value";
    public static final String INVALID_VALUE_FOR_AN_ENUM_ALLOWED_VALUES = "Invalid value for an enum. Allowed values: ";
    public static final String DELIMITER = ", ";
    public static final String INVALID_REQUEST_BODY = "Invalid request body";
    public static final String MALFORMED_JSON_OR_INCORRECT_DATA_TYPES = "Malformed JSON or incorrect data types.";
}
