package com.gymapp.shared.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gymapp.shared.error.ErrorConstants.*;


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

}
