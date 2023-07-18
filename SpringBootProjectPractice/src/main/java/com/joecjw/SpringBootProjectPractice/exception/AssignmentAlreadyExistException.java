package com.joecjw.SpringBootProjectPractice.exception;

public class AssignmentAlreadyExistException extends RuntimeException{
    public AssignmentAlreadyExistException() {
        super();
    }

    public AssignmentAlreadyExistException(String message) {
        super(message);
    }

    public AssignmentAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssignmentAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected AssignmentAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
