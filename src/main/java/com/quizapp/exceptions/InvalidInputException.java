package com.quizapp.exceptions;

/**
 * Exception thrown when invalid input is provided to the application.
 * This can include null values, empty strings, or invalid data formats.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}