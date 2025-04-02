package com.brock.companies.exception;

public class UpdateException extends RuntimeException {
    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}