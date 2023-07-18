package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.Student;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import com.joecjw.SpringBootProjectPractice.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {

    private StudentService studentService;

    private final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() throws StudentNotFoundException {
        LOGGER.info("Inside getAllStudents() of StudentController");
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.FOUND);
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable(name = "courseId") Long courseId) throws CourseNotFoundException {
        LOGGER.info("Inside getStudentsByCourseId() of StudentController," +
                "With PathVariable courseId=" + courseId);
        List<Student> studentList = new ArrayList<>();
        studentService.getStudentsByCourseId(courseId).forEach(studentList::add);
        if(studentList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentList, HttpStatus.FOUND);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable(name = "id") Long id) throws StudentNotFoundException {
        LOGGER.info("Inside getStudentById() of StudentController," +
                "With PathVariable id=" + id);
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.FOUND);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(value = "id") Long id,
                                                  @Valid@RequestBody Student student) throws StudentNotFoundException {
        LOGGER.info("Inside updateStudentById() of StudentController" +
                "With PathVariable id=" + id + " and Validated RequestBody student=" + student);
        return new ResponseEntity<>(studentService.updateStudentById(id, student), HttpStatus.OK);
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable(value = "studentId") Long studentId,
                                                @PathVariable(value = "courseId") Long courseId)
            throws StudentNotFoundException, CourseNotFoundException {
        LOGGER.info("Inside addStudentToCourse() of StudentController" +
                "With PathVariable studentId=" + studentId +
                " and PathVariable courseId=" + courseId );
        String result = studentService.addStudentToCourse(studentId, courseId);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable(value = "studentId") Long studentId,
                                                @PathVariable(value = "courseId") Long courseId)
            throws StudentNotFoundException, CourseNotFoundException {
        LOGGER.info("Inside removeStudentFromCourse() of StudentController" +
                "With PathVariable studentId=" + studentId +
                " and PathVariable courseId=" + courseId );
        String result = studentService.removeStudentFromCourse(studentId, courseId);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/students")
    public ResponseEntity<?> deleteAllStudents() {
        LOGGER.info("Inside deleteAllStudents() of StudentController");
        studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable(value = "id") Long id) throws StudentNotFoundException {
        LOGGER.info("Inside deleteStudentById() of StudentController" +
                "With PathVariable id=" + id);
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
