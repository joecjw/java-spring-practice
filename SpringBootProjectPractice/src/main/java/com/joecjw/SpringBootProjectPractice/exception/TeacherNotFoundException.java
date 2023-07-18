package com.joecjw.SpringBootProjectPractice.exception;

public class TeacherNotFoundException extends Exception{
    public TeacherNotFoundException() {
        super();
    }

    public TeacherNotFoundException(String message) {
        super(message);
    }

    public TeacherNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeacherNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TeacherNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
