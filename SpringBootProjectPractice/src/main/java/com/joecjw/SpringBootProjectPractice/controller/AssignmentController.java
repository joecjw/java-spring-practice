package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.Assignment;
import com.joecjw.SpringBootProjectPractice.model.FileResponseModel;
import com.joecjw.SpringBootProjectPractice.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AssignmentController {

    private AssignmentService assignmentService;

    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<?> getAssignmentById(@PathVariable(name = "assignmentId") Long assignmentId){
        return  ResponseEntity
                .status(HttpStatus.FOUND)
                .body(assignmentService.getAssignmentById(assignmentId));
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> getAllAssignments(){
        List<Assignment> assignmentList = new ArrayList<>();
        assignmentService.getAllAssignments().forEach(assignmentList::add);
        if(assignmentList.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
        return  ResponseEntity
                .status(HttpStatus.FOUND)
                .body(assignmentList);
    }

    @PostMapping("/assignments")
    public ResponseEntity<?> createNewAssignment(@Valid @RequestBody Assignment assignment,
                                                  @RequestParam(name = "time") String time){
        Assignment _assignment = assignmentService.createAssignment(assignment, time);
        if(_assignment == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Time String for Setting the Assignment's DDL\n" +
                            "The available time format should be in 'days:hours:minutes:seconds'\n");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(_assignment);
    }

    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@Valid @RequestBody Assignment assignment,
                                                  @PathVariable(name = "assignmentId") Long assignmentId,
                                                  @RequestParam(name = "time") String time){
        Assignment _assignment = assignmentService.updateAssignmentById(assignmentId, assignment, time);
        if(_assignment == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Time String for Setting the Assignment's DDL\n" +
                            "The available time format should be in 'days:hours:minutes:seconds'\n");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(_assignment);
    }

    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<?> deleteAssignmentById(@PathVariable(name = "assignmentId") Long assignmentId){
       assignmentService.deleteAssignmentById(assignmentId);
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(null);
    }

    @DeleteMapping("/assignments")
    public ResponseEntity<?> deleteAllAssignments() {
        assignmentService.deleteAllAssignments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PostMapping("/assignments/{assignmentId}/files/{fileId}")
    public ResponseEntity<?> addFileToAssignmentById(@PathVariable(name = "assignmentId") Long assignmentId,
                                                     @PathVariable(name = "fileId") Long fileId){
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(assignmentService.addFileToAssignmentById(assignmentId, fileId));
    }

    @GetMapping("/assignments/{assignmentId}/files")
    public ResponseEntity<?> getAllFilesWithAssignmentId(@PathVariable(name = "assignmentId") Long assignmentId){
        List<FileResponseModel> fileResponseModelList = assignmentService.getAllFilesWithAssignmentId(assignmentId);
        if(fileResponseModelList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(fileResponseModelList);
    }
}
