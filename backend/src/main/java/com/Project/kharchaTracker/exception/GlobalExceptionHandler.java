package com.Project.kharchaTracker.exception;

import com.Project.kharchaTracker.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDto> build (HttpStatus status, ErrorCode code, String message,
                                                    HttpServletRequest req, Map<String, String> fieldErrors) {

        ErrorResponseDto body = new ErrorResponseDto(
                Instant.now(), status.value(), code, message, req.getRequestURI(), fieldErrors
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                            HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, e.getCode(), e.getMessage(), req, null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException e, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, e.getCode(), e.getMessage(), req, null);
    }

    // 400 : Client sent invalid input
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                                  HttpServletRequest req) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.merge(error.getField(), error.getDefaultMessage(), (a, b) -> a + " ; " + b);
        });

        return build(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT, "Validation failed.", req, fieldErrors);
    }

    // 400 : constraints on @RequestParams/@PathVariables failed
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleHandlerMethodValidationException(HandlerMethodValidationException e,
                                                                                   HttpServletRequest req) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        e.getParameterValidationResults().forEach(result -> {
            String param = result.getMethodParameter().getParameterName();

            result.getResolvableErrors().forEach(err -> {
                String message = err.getDefaultMessage() != null ? err.getDefaultMessage() : "Invalid value";

                fieldErrors.merge(param, message, (a, b) -> a + " ; " + b);
            });
        });

        return build(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PARAMETER, "Invalid request parameter", req, fieldErrors);
    }

    // 400 : JSON coming is broken/invalid (Jackson can't build DTO)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                                  HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ErrorCode.MALFORMED_REQUEST,
                "Request body is missing, malformed, or has a wrong data type", req, null);
    }

    // 400 : Invalid/Broken method argument
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                                                      HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PARAMETER,
                "Invalid value for parameter '" + e.getName() + "'", req, null);
    }

    // 400 : Request parameter is missing
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                                          HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ErrorCode.MISSING_PARAMETER,
                "Missing required parameter '" + e.getParameterName() + "'", req, null);
    }

    // 405 : Wrong request for wrong endpoint
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                                         HttpServletRequest req) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.METHOD_NOT_ALLOWED,
                "HTTP method '" + e.getMethod() + "' is not supported here", req, null);
    }

    // 415 : Invalid media type
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
                                                                                     HttpServletRequest req) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.UNSUPPORTED_MEDIA_TYPE,
                "Content-Type must be application/json", req, null);
    }

    // 404 : Unknown URL
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ErrorCode.ENDPOINT_NOT_FOUND, "Endpoint not found", req, null);
    }

    // 409 : DB constraint violated
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                                                  HttpServletRequest req) {

        log.warn("Data integrity violation on {}", req.getRequestURI());
        return build(HttpStatus.CONFLICT, ErrorCode.DATA_CONFLICT, "Request conflicts with existing data", req, null);
    }

    // 500 : Anything I couldn't think of
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e, HttpServletRequest req) {

        log.error("Unexpected error on {} {}", req.getMethod(), req.getRequestURI(), e);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR, "Something went wrong", req, null);
    }
}
