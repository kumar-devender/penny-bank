package com.pennybank.elasticservice.errors;


public class MalFormedJsonException extends RuntimeException {
    public MalFormedJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
