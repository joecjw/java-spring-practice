package com.joecjw.Oauth2AuthorizationServer.exception;

public class UserEmailNotFoundException extends RuntimeException{
    public UserEmailNotFoundException() {
        super();
    }

    public UserEmailNotFoundException(String message) {
        super(message);
    }

    public UserEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserEmailNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UserEmailNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
