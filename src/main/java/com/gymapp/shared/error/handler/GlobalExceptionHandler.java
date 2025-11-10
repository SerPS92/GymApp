package com.gymapp.shared.error.handler;

import com.gymapp.shared.error.exception.AppException;
import com.gymapp.shared.error.exception.ConflictException;
import com.gymapp.shared.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gymapp.shared.error.handler.GlobalExceptionHandlerConstants.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ProblemDetail> handleAppException(AppException ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                ex.getStatus(),
                ex.getMessage(),
                ex.getCode().name(),
                URN_PROBLEM + ex.getCode().name().toLowerCase(),
                req
        );

        return ResponseEntity
                .status(ex.getStatus())
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        return handleBindingErrors(ex.getBindingResult(), req);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBind(BindException ex, HttpServletRequest req) {
        return handleBindingErrors(ex.getBindingResult(), req);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.BAD_REQUEST,
                INVALID_REQUEST_PARAMETERS,
                ErrorCode.BAD_REQUEST.name(),
                URN_PROBLEM_BAD_REQUEST,
                req
        );

        pd.setProperty(ERRORS, List.of(
                Map.of(MESSAGE, ex.getMessage() != null ? ex.getMessage() : INVALID_REQUEST_PARAMETERS)
        ));

        log.warn("Illegal argument at {}: {}", req.getRequestURI(), ex.getMessage());

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.BAD_REQUEST,
                VALIDATION_FAILED,
                ErrorCode.VALIDATION_ERROR.name(),
                URN_PROBLEM_VALIDATION_ERROR,
                req
        );

        List<Map<String, Object>> errors = ex.getConstraintViolations().stream()
                .map(v -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put(FIELD, v.getPropertyPath() != null ? v.getPropertyPath().toString() : "");
                    m.put(MESSAGE, v.getMessage() != null ? v.getMessage() : "");
                    m.put(REJECTED_VALUE, v.getInvalidValue() != null ? v.getInvalidValue() : "");
                    return m;
                })
                .toList();

        pd.setProperty(ERRORS, errors);

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest req) {
        HttpStatusCode statusCode = ex.getStatusCode();
        HttpStatus status = HttpStatus.resolve(statusCode.value());
        String reason = ex.getReason() != null ? ex.getReason() : (status != null ? status.getReasonPhrase() : ERROR);

        String code = (status == HttpStatus.NOT_FOUND ? NOT_FOUND : ERROR);
        String type = URN_PROBLEM + code.toLowerCase();

        ProblemDetail pd = createBaseProblemDetail(
                status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR,
                reason,
                code,
                type,
                req
        );

        return ResponseEntity
                .status(statusCode)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ProblemDetail> handleConflict(ConflictException ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                HttpStatus.CONFLICT.name(),
                URN_PROBLEM_CONFLICT,
                req
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.CONFLICT,
                DATA_INTEGRITY_VIOLATION,
                HttpStatus.CONFLICT.name(),
                URN_PROBLEM_CONFLICT,
                req
        );

        log.warn("Data integrity violation at {}: {}", req.getRequestURI(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        Throwable cause = ex.getCause();

        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife
                && ife.getTargetType() != null
                && ife.getTargetType().isEnum()) {

            String allowed = Arrays.stream(ife.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(DELIMITER));

            ProblemDetail pd = createBaseProblemDetail(
                    HttpStatus.BAD_REQUEST,
                    INVALID_VALUE_FOR_AN_ENUM_ALLOWED_VALUES + allowed,
                    HttpStatus.BAD_REQUEST.name(),
                    URN_PROBLEM_INVALID_ENUM,
                    req
            );
            pd.setTitle(INVALID_ENUM_VALUE);

            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(pd);
        }

        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.BAD_REQUEST,
                MALFORMED_JSON_OR_INCORRECT_DATA_TYPES,
                HttpStatus.BAD_REQUEST.name(),
                URN_PROBLEM_BAD_REQUEST,
                req
        );
        pd.setTitle(INVALID_REQUEST_BODY);

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.BAD_REQUEST,
                INVALID_REQUEST_PARAMETERS,
                HttpStatus.BAD_REQUEST.name(),
                URN_PROBLEM_BAD_REQUEST,
                req
        );

        pd.setProperty(ERRORS, List.of(Map.of(
                FIELD, ex.getName(),
                REJECTED_VALUE, ex.getValue() != null ? ex.getValue() : "",
                MESSAGE, INVALID_VALUE_FOR_PARAMETER
        )));

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                UNEXPECTED_ERROR,
                ErrorCode.INTERNAL_ERROR.name(),
                URN_PROBLEM_INTERNAL_ERROR,
                req
        );

        pd.setProperty(ERRORS, List.of(
                Map.of(MESSAGE, ex.getMessage() != null ? ex.getMessage() : INVALID_REQUEST_PARAMETERS)
        ));

        log.error("Unexpected error at {}: {}", req.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }


    private ProblemDetail createBaseProblemDetail(HttpStatus status, String detail, String code, String type, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty(CODE, code);
        pd.setType(URI.create(type));
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty(PATH, req.getRequestURI());
        return pd;
    }

    private List<Map<String, Object>> mapFieldErrors(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fe -> Map.of(
                        FIELD, fe.getField(),
                        MESSAGE, fe.getDefaultMessage() == null ? "" : fe.getDefaultMessage(),
                        REJECTED_VALUE, fe.getRejectedValue() == null ? "" : fe.getRejectedValue()
                ))
                .toList();
    }

    private ResponseEntity<ProblemDetail> handleBindingErrors(BindingResult result, HttpServletRequest req) {
        ProblemDetail pd = createBaseProblemDetail(
                HttpStatus.BAD_REQUEST,
                VALIDATION_FAILED,
                ErrorCode.VALIDATION_ERROR.name(),
                URN_PROBLEM_VALIDATION_ERROR,
                req
        );
        pd.setProperty(ERRORS, mapFieldErrors(result));
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(pd);
    }




}
