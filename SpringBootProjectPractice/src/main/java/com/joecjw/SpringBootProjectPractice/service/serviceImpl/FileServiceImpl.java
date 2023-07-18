package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.File;
import com.joecjw.SpringBootProjectPractice.exception.FileAlreadyExistException;
import com.joecjw.SpringBootProjectPractice.exception.FileNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.FileStorageException;
import com.joecjw.SpringBootProjectPractice.repository.FileRepository;
import com.joecjw.SpringBootProjectPractice.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    public File storeFile(MultipartFile file) {
        String fileName = null;
        try {
            // Normalize file name
            fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            if(fileRepository.findByName(fileName).isPresent()){
                throw new FileAlreadyExistException("The file with name: " + fileName + " is Already Exist");
            }

            File _file = new File();
            _file.setName(file.getOriginalFilename());
            _file.setType(file.getContentType());
            _file.setData(file.getBytes());
            _file.setSize(file.getSize());

            return fileRepository.save(_file);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public File getFileById(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    public List<File> getAllFiles() {
        List<File> files = new ArrayList<>();
        fileRepository.findAll().forEach(files::add);
        return files;
    }

}
