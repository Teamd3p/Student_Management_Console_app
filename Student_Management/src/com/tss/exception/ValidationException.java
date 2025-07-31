package com.tss.exception;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super("Validation Error: "+message);
    }
}
