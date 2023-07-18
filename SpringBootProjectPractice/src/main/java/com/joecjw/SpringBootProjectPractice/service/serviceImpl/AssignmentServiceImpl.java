package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.Assignment;
import com.joecjw.SpringBootProjectPractice.entity.File;
import com.joecjw.SpringBootProjectPractice.exception.AssignmentAlreadyExistException;
import com.joecjw.SpringBootProjectPractice.exception.AssignmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.FileNotFoundException;
import com.joecjw.SpringBootProjectPractice.model.FileResponseModel;
import com.joecjw.SpringBootProjectPractice.repository.AssignmentRepository;
import com.joecjw.SpringBootProjectPractice.repository.FileRepository;
import com.joecjw.SpringBootProjectPractice.service.AssignmentService;
import com.joecjw.SpringBootProjectPractice.utils.TimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentRepository assignmentRepository;

    private FileRepository fileRepository;

    @Override
    public Assignment createAssignment(Assignment assignment, String time) {
        if(!assignmentRepository.findAssignmentByName(assignment.getName()).isPresent()){
            Assignment _assignment = new Assignment();
            _assignment.setName(assignment.getName());
            _assignment.setDescription(assignment.getDescription());
            if(TimeUtil.parseDDL(time) == null){
                return null;
            }
            _assignment.setDeadLine(TimeUtil.parseDDL(time));
            return assignmentRepository.save(_assignment);
        }else {
            throw new AssignmentAlreadyExistException("Assignment with name: "+assignment.getName()+" Already Exist");
        }
    }

    @Override
    public Assignment getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(()-> new AssignmentNotFoundException("Assignment with id: " + assignmentId + "Not Found"));

    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment updateAssignmentById(Long assignmentId, Assignment assignment, String time) {
        Assignment _assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()-> new AssignmentNotFoundException("Assignment with id: " + assignmentId + "Not Found"));
        _assignment.setName(assignment.getName());
        _assignment.setDescription(assignment.getDescription());
        if(TimeUtil.parseDDLFromCreationTime(time, assignment.getCreationTime()) != null){
            _assignment.setDeadLine(TimeUtil.parseDDLFromCreationTime(time, assignment.getCreationTime()));
        }else {
            return null;
        }
        return assignmentRepository.save(_assignment);
    }

    @Override
    public void deleteAssignmentById(Long assignmentId) {
        if(!assignmentRepository.existsById(assignmentId)){
            throw new AssignmentNotFoundException("Assignment with id: " + assignmentId + "Not Found");
        }
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    public void deleteAllAssignments() {
        assignmentRepository.deleteAll();

    }

    @Override
    public String addFileToAssignmentById(Long assignmentId, Long fileId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()-> new AssignmentNotFoundException("Assignment with id: " + assignmentId + "Not Found"));
        File file = fileRepository.findById(fileId)
                .orElseThrow(()-> new FileNotFoundException("File with id: " + fileId + "Not Found"));
        assignment.getFiles().add(file);
        assignmentRepository.save(assignment);
        return "File with id: " + fileId + "Has Successfully Added To Assignment with id: " + assignmentId ;
    }

    @Override
    public List<FileResponseModel> getAllFilesWithAssignmentId(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(()-> new AssignmentNotFoundException("Assignment with id: " + assignmentId + "Not Found"));
        List<FileResponseModel> fileResponseModelList = new ArrayList<>();
        assignment.getFiles().forEach(file -> {
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(String.valueOf(file.getId()))
                    .toUriString();

            fileResponseModelList.add(new FileResponseModel(
                    file.getId(),
                    file.getName(),
                    file.getType(),
                    downloadUrl,
                    file.getSize(),
                    file.getCreationTime(),
                    file.getLastModifiedTime()
            ));
        });
        return fileResponseModelList;
    }
}
