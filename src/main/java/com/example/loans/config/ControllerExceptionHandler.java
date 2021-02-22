package com.example.loans.config;

import com.example.loans.exceptions.ApiError;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ApiError> userNotFoundException(HttpServletRequest req, UserNotFoundException ex) {
        LOGGER.info(ex.getMessage());
        ApiError apiError = new ApiError("Error in User ID", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {PageNotFoundException.class})
    public ResponseEntity<ApiError> pageNotFoundException(HttpServletRequest req, PageNotFoundException ex) {
        LOGGER.info(ex.getMessage());
        ApiError apiError = new ApiError("Error in Page number", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> missingParamsException(HttpServletRequest req, MissingServletRequestParameterException ex) {
        LOGGER.info(ex.getMessage());
        ApiError apiError = new ApiError("Missing Parameter Exception", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ApiError> missingParamsException(HttpServletRequest req, ConstraintViolationException ex) {
        LOGGER.info(ex.getMessage());
        ApiError apiError = new ApiError("Constraint Violation Exception", ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

}
