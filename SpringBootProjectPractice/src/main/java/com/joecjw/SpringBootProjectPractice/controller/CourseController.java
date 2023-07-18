package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import com.joecjw.SpringBootProjectPractice.service.CourseService;
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
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    private final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() throws CourseNotFoundException {
        LOGGER.info("Inside getAllCourses() of CourseController");
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.FOUND);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable(name = "id") Long id) throws CourseNotFoundException {
        LOGGER.info("Inside getCourseById() of CourseController," +
                "With PathVariable id=" + id);
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.FOUND);
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<?> getCoursesByStudentId(@PathVariable(name = "studentId") Long studentId) throws StudentNotFoundException {
        LOGGER.info("Inside getCoursesByStudentId() of StudentController," +
                "With PathVariable studentId=" + studentId);
        List<Course> courseList = new ArrayList<>();
        courseService.getCoursesByStudentId(studentId).forEach(courseList::add);
        if(courseList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseList, HttpStatus.FOUND);
    }

    @PostMapping("/courses")
    public ResponseEntity<?> saveCourse(@Valid@RequestBody Course course) {
        LOGGER.info("Inside saveCourse() of CourseController" +
                "With Validated RequestBody course=" + course);
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @PostMapping("/courses/material")
    public ResponseEntity<?> saveCourseWithCourseDetail(@Valid @RequestBody Course course) {
        LOGGER.info("Inside saveCourseWithCourseDetail() of CourseController" +
                "With Validated RequestBody course=" + course);
        return new ResponseEntity<>(courseService.saveCourseWithCourseDetail(course), HttpStatus.CREATED);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourseById(@PathVariable(value = "id") Long id,
                                              @Valid@RequestBody Course course) throws CourseNotFoundException {
        LOGGER.info("Inside updateCourseById() of CourseController" +
                "With PathVariable id=" + id + " and Validated RequestBody course=" + course);
        return new ResponseEntity<>(courseService.updateCourseById(id, course), HttpStatus.OK);
    }

    @DeleteMapping("/courses")
    public ResponseEntity<?> deleteAllCourse() {
        LOGGER.info("Inside deleteAllCourse() of CourseController");
        courseService.deleteAllCourse();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable(value = "id") Long id) throws CourseNotFoundException {
        LOGGER.info("Inside deleteCourseById() of CourseController" +
                "With PathVariable id=" + id);
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
