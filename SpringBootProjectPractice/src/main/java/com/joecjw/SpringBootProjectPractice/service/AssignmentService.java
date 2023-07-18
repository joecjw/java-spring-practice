package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.Assignment;
import com.joecjw.SpringBootProjectPractice.entity.File;
import com.joecjw.SpringBootProjectPractice.model.FileResponseModel;

import java.util.List;

public interface AssignmentService {

    Assignment createAssignment(Assignment assignment, String time);

    Assignment getAssignmentById(Long assignmentId);

    List<Assignment> getAllAssignments();

    Assignment updateAssignmentById(Long assignmentId, Assignment assignment, String time);

    void deleteAssignmentById(Long assignmentId);

    void deleteAllAssignments();

    String addFileToAssignmentById(Long assignmentId, Long fileId);

    List<FileResponseModel> getAllFilesWithAssignmentId(Long assignmentId);
}
