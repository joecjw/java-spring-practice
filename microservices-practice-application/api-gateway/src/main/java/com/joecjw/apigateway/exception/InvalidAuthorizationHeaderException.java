package com.joecjw.apigateway.exception;

public class InvalidAuthorizationHeaderException extends RuntimeException {
    public InvalidAuthorizationHeaderException() {
        super();
    }

    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }

    public InvalidAuthorizationHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthorizationHeaderException(Throwable cause) {
        super(cause);
    }

    protected InvalidAuthorizationHeaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
