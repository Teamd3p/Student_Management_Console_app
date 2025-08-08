package com.tss.exception;

public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super("Validation Error: " + message);
    }
}
