package com.joecjw.SpringBootProjectPractice.exception;

public class AssignmentNotFoundException extends RuntimeException{
    public AssignmentNotFoundException() {
        super();
    }

    public AssignmentNotFoundException(String message) {
        super(message);
    }

    public AssignmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssignmentNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AssignmentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
