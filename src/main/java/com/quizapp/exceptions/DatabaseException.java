package com.quizapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

