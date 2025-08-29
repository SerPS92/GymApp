package com.gymapp.shared.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gymapp.shared.error.GlobalExceptionHandlerConstants.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        pd.setTitle(ex.getStatus().getReasonPhrase());
        pd.setProperty(CODE, ex.getCode().name());
        pd.setProperty(PATH, req.getRequestURI());
        pd.setType(URI.create(URN_PROBLEM + ex.getCode().name().toLowerCase()));
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, VALIDATION_FAILED);
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty(CODE, ErrorCode.VALIDATION_ERROR.name());
        pd.setType(URI.create(URN_PROBLEM_VALIDATION_ERROR));
        pd.setInstance(URI.create(req.getRequestURI()));

        List<Map<String, Object>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put(FIELD, fe.getField());
                    m.put(MESSAGE, fe.getDefaultMessage());
                    m.put(REJECTED_VALUE, fe.getRejectedValue()); // puede ser null
                    return m;
                })
                .toList();

        pd.setProperty(ERRORS, errors);
        pd.setProperty(PATH, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(BindException.class)
    public ProblemDetail handleBind(BindException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, VALIDATION_FAILED);
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty(CODE, ErrorCode.VALIDATION_ERROR.name());
        pd.setType(URI.create(URN_PROBLEM_VALIDATION_ERROR));
        pd.setInstance(URI.create(req.getRequestURI()));

        List<Map<String, Object>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put(FIELD, fe.getField());
                    m.put(MESSAGE, fe.getDefaultMessage());
                    m.put(REJECTED_VALUE, fe.getRejectedValue()); // puede ser null
                    return m;
                })
                .toList();

        pd.setProperty(ERRORS, errors);
        pd.setProperty(PATH, req.getRequestURI());
        return pd;
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        var pd = ProblemDetail.forStatusAndDetail(status, INVALID_REQUEST_PARAMETERS);
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty(CODE, ErrorCode.BAD_REQUEST.name());
        pd.setProperty(PATH, req.getRequestURI());
        pd.setType(URI.create(URN_PROBLEM_BAD_REQUEST));
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, UNEXPECTED_ERROR);
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty(CODE, ErrorCode.INTERNAL_ERROR.name());
        pd.setProperty(PATH, req.getRequestURI());
        pd.setType(URI.create(URN_PROBLEM_INTERNAL_ERROR));
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        var pd = ProblemDetail.forStatusAndDetail(status, VALIDATION_FAILED);
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty(CODE, ErrorCode.VALIDATION_ERROR.name());
        pd.setType(URI.create(URN_PROBLEM_VALIDATION_ERROR));
        pd.setInstance(URI.create(req.getRequestURI()));
        var errors = ex.getConstraintViolations().stream()
                .map(v -> Map.of(
                        FIELD, v.getPropertyPath().toString(),
                        MESSAGE, v.getMessage(),
                        REJECTED_VALUE, v.getInvalidValue()))
                .toList();
        pd.setProperty(ERRORS, errors);
        pd.setProperty(PATH, req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleResponseStatusException(
            org.springframework.web.server.ResponseStatusException ex,
            jakarta.servlet.http.HttpServletRequest request) {

        org.springframework.http.HttpStatusCode status = ex.getStatusCode();
        org.springframework.http.HttpStatus http = org.springframework.http.HttpStatus.resolve(status.value());

        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle(http != null ? http.getReasonPhrase() : ERROR);
        pd.setDetail(ex.getReason() != null ? ex.getReason() : pd.getTitle());
        pd.setType(java.net.URI.create(URN_PROBLEM + (
                http == org.springframework.http.HttpStatus.NOT_FOUND ? NOT_FOUND : ERROR)));
        pd.setInstance(java.net.URI.create(request.getRequestURI()));
        pd.setProperty(PATH, request.getRequestURI());
        pd.setProperty(CODE, (http == HttpStatus.NOT_FOUND ? NOT_FOUND : ERROR));
        return ResponseEntity.status(status).body(pd);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ProblemDetail> handleConflict(
            ConflictException ex,
            jakarta.servlet.http.HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(org.springframework.http.HttpStatus.CONFLICT);
        pd.setTitle(CONFLICT);
        pd.setDetail(ex.getMessage());
        pd.setType(java.net.URI.create(URN_PROBLEM_CONFLICT));
        pd.setInstance(java.net.URI.create(request.getRequestURI()));
        pd.setProperty(PATH, request.getRequestURI());
        pd.setProperty(CODE, HttpStatus.CONFLICT.name());
        return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrity(
            org.springframework.dao.DataIntegrityViolationException ex,
            jakarta.servlet.http.HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(org.springframework.http.HttpStatus.CONFLICT);
        pd.setTitle(CONFLICT);
        pd.setDetail(DATA_INTEGRITY_VIOLATION);
        pd.setType(java.net.URI.create(URN_PROBLEM_CONFLICT));
        pd.setInstance(java.net.URI.create(request.getRequestURI()));
        pd.setProperty(PATH, request.getRequestURI());
        pd.setProperty(CODE, HttpStatus.CONFLICT.name());
        return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        Throwable cause = ex.getCause();

        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife
                && ife.getTargetType() != null
                && ife.getTargetType().isEnum()) {

            String allowed = java.util.Arrays.stream(ife.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(java.util.stream.Collectors.joining(DELIMITER));

            ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            pd.setTitle(INVALID_ENUM_VALUE);
            pd.setDetail(INVALID_VALUE_FOR_AN_ENUM_ALLOWED_VALUES + allowed);
            pd.setType(java.net.URI.create(URN_PROBLEM_INVALID_ENUM));
            pd.setInstance(java.net.URI.create(request.getRequestURI()));
            pd.setProperty(PATH, request.getRequestURI());
            pd.setProperty(CODE, HttpStatus.BAD_REQUEST.name());
            return ResponseEntity.badRequest().body(pd);
        }

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle(INVALID_REQUEST_BODY);
        pd.setDetail(MALFORMED_JSON_OR_INCORRECT_DATA_TYPES);
        pd.setType(java.net.URI.create(URN_PROBLEM_BAD_REQUEST));
        pd.setInstance(java.net.URI.create(request.getRequestURI()));
        pd.setProperty(PATH, request.getRequestURI());
        pd.setProperty(CODE, HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex,
            HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        var pd = ProblemDetail.forStatusAndDetail(status, INVALID_REQUEST_PARAMETERS);
        pd.setTitle(status.getReasonPhrase());
        pd.setType(URI.create(URN_PROBLEM_BAD_REQUEST));
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty(CODE, HttpStatus.BAD_REQUEST.name());
        pd.setProperty(PATH, req.getRequestURI());
        return pd;
    }

}
