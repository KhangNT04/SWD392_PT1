package com.example.library.business.exception;

public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException(String message) {
        super(message);
    }
}
