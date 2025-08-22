package com.gymapp.shared.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        pd.setTitle(ex.getStatus().getReasonPhrase());
        pd.setProperty("code", ex.getCode().name());
        pd.setProperty("path", req.getRequestURI());
        pd.setType(URI.create("urn:problem:" + ex.getCode().name().toLowerCase()));
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, "Validation failed");
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty("code", ErrorCode.VALIDATION_ERROR.name());
        pd.setType(URI.create("urn:problem:validation_error"));
        pd.setInstance(URI.create(req.getRequestURI()));

        List<Map<String, Object>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of(
                        "field", fe.getField(),
                        "message", fe.getDefaultMessage(),
                        "rejectedValue", fe.getRejectedValue()
                ))
                .toList();

        pd.setProperty("errors", errors);
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(BindException.class)
    public ProblemDetail handleBind(BindException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, "Validation failed");
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty("code", ErrorCode.VALIDATION_ERROR.name());
        pd.setType(URI.create("urn:problem:validation_error"));
        pd.setInstance(URI.create(req.getRequestURI()));

        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> Map.of(
                        "field", fe.getField(),
                        "message", fe.getDefaultMessage(),
                        "rejectedValue", fe.getRejectedValue()
                ))
                .toList();
        pd.setProperty("errors", errors);
        pd.setProperty("path", req.getRequestURI());
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        var status = HttpStatus.BAD_REQUEST;
        var pd = ProblemDetail.forStatusAndDetail(status, "Invalid request parameters");
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty("code", ErrorCode.BAD_REQUEST.name());
        pd.setType(URI.create("urn:problem:bad_request"));
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, "Unexpected error");
        pd.setTitle(status.getReasonPhrase());
        pd.setProperty("code", ErrorCode.INTERNAL_ERROR.name());
        pd.setProperty("path", req.getRequestURI());
        pd.setType(URI.create("urn:problem:validation_error"));
        pd.setInstance(URI.create(req.getRequestURI()));
        return pd;
    }
}
