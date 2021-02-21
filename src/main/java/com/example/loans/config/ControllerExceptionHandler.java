package com.example.loans.config;

import com.example.loans.exceptions.ApiError;
import com.example.loans.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ApiError> actionEventNotFoundException(HttpServletRequest req, UserNotFoundException ex) {
        LOGGER.info(ex.getMessage());
        ApiError apiError = new ApiError("Error in User ID", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

}
