package com.joecjw.SpringBootProjectPractice.exception;

public class CourseMaterialNotFoundException extends Exception{
    public CourseMaterialNotFoundException() {
        super();
    }

    public CourseMaterialNotFoundException(String message) {
        super(message);
    }

    public CourseMaterialNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseMaterialNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CourseMaterialNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
