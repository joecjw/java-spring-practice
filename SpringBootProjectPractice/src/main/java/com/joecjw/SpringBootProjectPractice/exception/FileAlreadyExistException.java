package com.joecjw.SpringBootProjectPractice.exception;

public class FileAlreadyExistException extends RuntimeException{
    public FileAlreadyExistException() {
        super();
    }

    public FileAlreadyExistException(String message) {
        super(message);
    }

    public FileAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected FileAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
