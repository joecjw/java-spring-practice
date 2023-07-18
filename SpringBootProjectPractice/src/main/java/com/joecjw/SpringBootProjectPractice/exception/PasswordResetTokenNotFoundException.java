package com.joecjw.SpringBootProjectPractice.exception;

public class PasswordResetTokenNotFoundException extends Exception{
    public PasswordResetTokenNotFoundException() {
        super();
    }

    public PasswordResetTokenNotFoundException(String message) {
        super(message);
    }

    public PasswordResetTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PasswordResetTokenNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
