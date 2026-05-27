package com.example.library.business.exception;

public class InvalidBorrowingRequestException extends RuntimeException {

    public InvalidBorrowingRequestException(String message) {
        super(message);
    }
}
