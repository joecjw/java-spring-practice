package com.joecjw.JwtPractice.exception.handler;

import com.joecjw.JwtPractice.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("status",HttpStatus.FORBIDDEN);
        response.put("timestamp", Instant.now().toString());
        response.put("discription", request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
