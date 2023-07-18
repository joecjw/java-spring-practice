package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    public File storeFile(MultipartFile file);

    public File getFileById(Long fileId);

    public List<File> getAllFiles();
}
