package com.example.loans.exceptions;

public class LoansCreationForbiddenException extends RuntimeException {

    private String message;

    public LoansCreationForbiddenException(String message) {
        super(message);
    }
}