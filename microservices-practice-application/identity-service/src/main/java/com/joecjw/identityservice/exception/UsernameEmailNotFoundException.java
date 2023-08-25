package com.joecjw.identityservice.exception;

public class UsernameEmailNotFoundException extends RuntimeException {
    public UsernameEmailNotFoundException() {
        super();
    }

    public UsernameEmailNotFoundException(String message) {
        super(message);
    }

    public UsernameEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameEmailNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UsernameEmailNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}