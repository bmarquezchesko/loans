package com.example.loans.config;

import com.example.loans.exceptions.ApiError;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ApiError> userNotFoundException(HttpServletRequest req, UserNotFoundException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("User not found Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {PageNotFoundException.class})
    public ResponseEntity<ApiError> pageNotFoundException(HttpServletRequest req, PageNotFoundException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Page not found Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> missingParamsException(HttpServletRequest req, MissingServletRequestParameterException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Missing Parameter Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ApiError> constraintViolationException(HttpServletRequest req, ConstraintViolationException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Constraint Violation Exception", ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<ApiError> numberFormatException(HttpServletRequest req, NumberFormatException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Number Format Exception", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<ApiError> emptyResultDataException(HttpServletRequest req, EmptyResultDataAccessException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Empty Result Data Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        LOGGER.warn(String.format("Exception %s was thrown", ex.getClass()));

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Method Argument Not Valid");

        //Get all errors
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        LOGGER.warn(String.format("Errors: %s", errors));

        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }

}
