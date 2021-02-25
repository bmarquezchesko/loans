package com.example.loans.config;

import com.example.loans.exceptions.ApiError;
import com.example.loans.exceptions.LoansCreationForbiddenException;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ApiError> userNotFoundException(UserNotFoundException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("User not found Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {PageNotFoundException.class})
    public ResponseEntity<ApiError> pageNotFoundException(PageNotFoundException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Page not found Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> missingParamsException(MissingServletRequestParameterException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Missing Parameter Exception", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ApiError> constraintViolationException(ConstraintViolationException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Constraint Violation Exception", ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<ApiError> numberFormatException(NumberFormatException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Number Format Exception", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<ApiError> emptyResultDataException(EmptyResultDataAccessException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Empty Result Data Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Argument Type Mismatch Exception", ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {LoansCreationForbiddenException.class})
    public ResponseEntity<ApiError> loansCreationForbiddenException(LoansCreationForbiddenException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Loans Creation Forbidden Exception", ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
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

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError> handleUnknownException(HttpMessageNotReadableException ex) {
        LOGGER.warn(String.format("Exception %s was thrown with message: %s", ex.getClass(), ex.getMessage()));
        ApiError apiError = new ApiError("Internal Error", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

}
