package com.joecjw.SpringBootProjectPractice.exception;

public class InvalidUserRoleException extends Exception{
    public InvalidUserRoleException() {
        super();
    }

    public InvalidUserRoleException(String message) {
        super(message);
    }

    public InvalidUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserRoleException(Throwable cause) {
        super(cause);
    }

    protected InvalidUserRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
