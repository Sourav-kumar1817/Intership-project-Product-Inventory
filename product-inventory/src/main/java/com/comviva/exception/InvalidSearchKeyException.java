package com.comviva.exception;
public class InvalidSearchKeyException extends RuntimeException {
    public InvalidSearchKeyException(String message) {
        super(message);
    }
    public InvalidSearchKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
