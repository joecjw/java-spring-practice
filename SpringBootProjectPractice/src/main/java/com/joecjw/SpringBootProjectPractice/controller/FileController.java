package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.File;
import com.joecjw.SpringBootProjectPractice.exception.FileNotFoundException;
import com.joecjw.SpringBootProjectPractice.model.FileResponseModel;
import com.joecjw.SpringBootProjectPractice.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('Moderator')")
@AllArgsConstructor
public class FileController {

    private FileService fileService;

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<?> downloadFileById(@PathVariable Long fileId, HttpServletRequest request) {
        try {
        // Load file as Resource
        File file = fileService.getFileById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getName() + "\"")
                .body(new ByteArrayResource(file.getData()));

        }catch (FileNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFileDetailById(@PathVariable(name = "id") Long id) {
        try {
            // Load file as Resource
            File file = fileService.getFileById(id);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(String.valueOf(file.getId()))
                    .toUriString();

            FileResponseModel fileResponseModel = new FileResponseModel();
            fileResponseModel.setId(file.getId());
            fileResponseModel.setName(file.getName());
            fileResponseModel.setType(file.getType());
            fileResponseModel.setSize(file.getSize());
            fileResponseModel.setDownloadUrl(fileDownloadUri);
            fileResponseModel.setCreationTime(file.getCreationTime());
            fileResponseModel.setLastModifiedTime(file.getLastModifiedTime());

            return new ResponseEntity<>(fileResponseModel, HttpStatus.FOUND);

        }catch (FileNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<?> getAllFileDetailsById() {
        // Load files as Resource
        List<File> files = fileService.getAllFiles();
        if(files.isEmpty()){
             return new ResponseEntity<>("No Files has been uploaded", HttpStatus.NO_CONTENT);
        }else {
            List<FileResponseModel> fileResponseModelList = new ArrayList<>();
            files.forEach(file -> {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/downloadFile/")
                        .path(String.valueOf(file.getId()))
                        .toUriString();

                FileResponseModel fileResponseModel = new FileResponseModel();
                fileResponseModel.setId(file.getId());
                fileResponseModel.setName(file.getName());
                fileResponseModel.setType(file.getType());
                fileResponseModel.setSize(file.getSize());
                fileResponseModel.setDownloadUrl(fileDownloadUri);
                fileResponseModel.setCreationTime(file.getCreationTime());
                fileResponseModel.setLastModifiedTime(file.getLastModifiedTime());
                fileResponseModelList.add(fileResponseModel);
            });
            return new ResponseEntity<>("Total Number of Files: "+ fileResponseModelList.size()+"\n"
                                        +fileResponseModelList, HttpStatus.FOUND);
        }
    }

    @PostMapping("/upload/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        File _file = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(String.valueOf(_file.getId()))
                .toUriString();

        FileResponseModel fileResponseModel = new FileResponseModel();
        fileResponseModel.setId(_file.getId());
        fileResponseModel.setName(_file.getName());
        fileResponseModel.setType(_file.getType());
        fileResponseModel.setSize(_file.getSize());
        fileResponseModel.setDownloadUrl(fileDownloadUri);
        fileResponseModel.setCreationTime(_file.getCreationTime());
        fileResponseModel.setLastModifiedTime(_file.getLastModifiedTime());

        return new ResponseEntity<>(fileResponseModel, HttpStatus.CREATED);
    }

    @PostMapping("/upload/files")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    Arrays
                    .stream(files)
                    .map(file -> uploadFile(file))
                    .collect(Collectors.toList())
                );
    }
}
