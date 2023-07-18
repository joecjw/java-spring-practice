package com.joecjw.SpringBootProjectPractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseModel {

    private Long id;

    private String name;

    private String type;

    private String downloadUrl;

    private long size;

    private LocalDateTime creationTime;

    private LocalDateTime lastModifiedTime;
}
