package com.joecjw.SpringBootProjectPractice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {

    private HttpStatus status;

    private String message;
}
