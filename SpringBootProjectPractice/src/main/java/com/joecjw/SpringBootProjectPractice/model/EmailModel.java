package com.joecjw.SpringBootProjectPractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

    private final String SENDER_EMAIL = "joechan1520@gmail.com";

    private String to;

    private String subject;

    private String[] cc;

    private String body;

    private MultipartFile[] file;
}
