package com.example.loans.exceptions;

public class PageNotFoundException extends RuntimeException {

    private String message;

    public PageNotFoundException(String message) {
        super(message);
    }
}
