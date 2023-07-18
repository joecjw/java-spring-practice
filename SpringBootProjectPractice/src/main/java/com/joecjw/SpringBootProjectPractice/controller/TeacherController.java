package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.Teacher;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.TeacherNotFoundException;
import com.joecjw.SpringBootProjectPractice.service.TeacherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TeacherController {

    private TeacherService teacherService;

    private final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers() throws TeacherNotFoundException {
        LOGGER.info("Inside getAllTeachers() of TeacherController");
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.FOUND);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable(name = "id") Long id) throws TeacherNotFoundException {
        LOGGER.info("Inside getTeacherById() of TeacherController," +
                "With PathVariable id=" + id);
        return new ResponseEntity<>(teacherService.getTeacherById(id), HttpStatus.FOUND);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<?> updateTeacherById(@PathVariable(value = "id") Long id,
                                               @Valid@RequestBody Teacher teacher) throws TeacherNotFoundException {
        LOGGER.info("Inside updateTeacherById() of TeacherController" +
                "With PathVariable id=" + id + " and Validated RequestBody teacher=" + teacher);
        return new ResponseEntity<>(teacherService.updateTeacherById(id, teacher), HttpStatus.OK);
    }

    @PostMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<?> addTeacherToCourse(@PathVariable(value = "teacherId") Long teacherId,
                                                @PathVariable(value = "courseId") Long courseId) throws TeacherNotFoundException, CourseNotFoundException {
        LOGGER.info("Inside addTeacherToCourse() of TeacherController" +
                "With PathVariable teacherId=" + teacherId +
                " and PathVariable courseId=" + courseId );
        String result = teacherService.addTeacherToCourse(teacherId, courseId);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<?> removeTeacherFromCourse(@PathVariable(value = "teacherId") Long teacherId,
                                                     @PathVariable(value = "courseId") Long courseId) throws TeacherNotFoundException, CourseNotFoundException {
        LOGGER.info("Inside removeTeacherFromCourse() of TeacherController" +
                "With PathVariable teacherId=" + teacherId +
                " and PathVariable courseId=" + courseId );
        String result = teacherService.removeTeacherFromCourse(teacherId, courseId);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/teachers")
    public ResponseEntity<?> deleteAllTeachers() {
        LOGGER.info("Inside deleteAllTeachers() of TeacherController");
        teacherService.deleteAllTeachers();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<?> deleteTeacherById(@PathVariable(value = "id") Long id) throws TeacherNotFoundException {
        LOGGER.info("Inside deleteTeacherById() of TeacherController" +
                "With PathVariable id=" + id);
        teacherService.deleteTeacherById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
