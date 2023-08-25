package com.joecjw.identityservice.exception;

public class InvalidAuthHeaderException extends RuntimeException{
    public InvalidAuthHeaderException() {
        super();
    }

    public InvalidAuthHeaderException(String message) {
        super(message);
    }

    public InvalidAuthHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthHeaderException(Throwable cause) {
        super(cause);
    }

    protected InvalidAuthHeaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
