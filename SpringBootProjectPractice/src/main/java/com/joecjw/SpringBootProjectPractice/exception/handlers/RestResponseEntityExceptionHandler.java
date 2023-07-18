package com.joecjw.SpringBootProjectPractice.exception.handlers;

import com.joecjw.SpringBootProjectPractice.model.ExceptionMessage;
import com.joecjw.SpringBootProjectPractice.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        String msg = ex.getMessage();
        return super.handleExceptionInternal(ex, msg, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            errorMessages.add(objectError.getDefaultMessage());
        });

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errorMessages);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> departmentNotFoundException(DepartmentNotFoundException exception,
                                                                       WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(message);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> studentNotFoundException(StudentNotFoundException exception,
                                                                        WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<ExceptionMessage> teacherNotFoundException(TeacherNotFoundException exception,
                                                                      WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ExceptionMessage> courseNotFoundException(CourseNotFoundException exception,
                                                                     WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(CourseMaterialNotFoundException.class)
    public ResponseEntity<ExceptionMessage> courseMaterialNotFoundException(CourseMaterialNotFoundException exception,
                                                                     WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> userNotFoundException(UserNotFoundException exception,
                                                                            WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    public ResponseEntity<ExceptionMessage> verificationTokenNotFoundException(VerificationTokenNotFoundException exception,
                                                                               WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(PasswordResetTokenNotFoundException.class)
    public ResponseEntity<ExceptionMessage> passwordResetTokenNotFoundException(PasswordResetTokenNotFoundException exception,
                                                                               WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionMessage> userAlreadyExistException(UserAlreadyExistException exception,
                                                                                WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
    @ExceptionHandler(InvalidUserRoleException.class)
    public ResponseEntity<ExceptionMessage> invalidUserRoleException(InvalidUserRoleException exception,
                                                                      WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ExceptionMessage> fileStorageException(FileStorageException exception,
                                                                        WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ExceptionMessage> fileNotFoundException(FileNotFoundException exception,
                                                                        WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(FileAlreadyExistException.class)
    public ResponseEntity<ExceptionMessage> fileAlreadyExistException(FileAlreadyExistException exception,
                                                                  WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(AssignmentAlreadyExistException.class)
    public ResponseEntity<ExceptionMessage> assignmentAlreadyExistException(AssignmentAlreadyExistException exception,
                                                                  WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<ExceptionMessage> assignmentNotFoundException(AssignmentNotFoundException exception,
                                                                      WebRequest request){
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }
}
