package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.CourseMaterial;
import com.joecjw.SpringBootProjectPractice.exception.CourseMaterialNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.service.CourseMaterialService;
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
@PreAuthorize("hasRole('MODERATOR')")
@AllArgsConstructor
public class CourseMaterialController {

    private CourseMaterialService courseMaterialService;

    private final Logger LOGGER = LoggerFactory.getLogger(CourseMaterialController.class);

    @GetMapping("/course_materials")
    public ResponseEntity<?> getAllCourseMaterials() throws CourseMaterialNotFoundException {
        LOGGER.info("Inside getAllCourseMaterials() of CourseMaterialController");
        return new ResponseEntity<>(courseMaterialService.getAllCourseMaterials(), HttpStatus.FOUND);
    }

    @GetMapping("/course_materials/{id}")
    public ResponseEntity<?> getCourseMaterialById(@PathVariable(name = "id") Long id) throws CourseMaterialNotFoundException {
        LOGGER.info("Inside getCourseMaterialById() of CourseMaterialController," +
                "With PathVariable id=" + id);
        return new ResponseEntity<>(courseMaterialService.getCourseMaterialById(id), HttpStatus.FOUND);
    }

    @PostMapping("/courses/{courseId}/course_materials")
    public ResponseEntity<?> saveCourseMaterialByCourseId(@PathVariable(name = "courseId") Long courseId,
                                                          @Valid @RequestBody CourseMaterial courseMaterial) throws CourseNotFoundException {
        LOGGER.info("Inside saveCourseMaterialByCourseId() of CourseMaterialController" +
                "With Validated RequestBody courseMaterial=" + courseMaterial + " and Course Id= " + courseId);
        return new ResponseEntity<>(courseMaterialService.saveCourseMaterialByCourseId(courseId, courseMaterial), HttpStatus.CREATED);
    }

    @PutMapping("/course_materials/{id}")
    public ResponseEntity<?> updateCourseMaterialById(@PathVariable(value = "id") Long id,
                                               @Valid@RequestBody CourseMaterial courseMaterial) throws CourseMaterialNotFoundException {
        LOGGER.info("Inside updateCourseMaterialById() of CourseMaterialController" +
                "With PathVariable id=" + id + " and Validated RequestBody courseMaterial=" + courseMaterial);
        return new ResponseEntity<>(courseMaterialService.updateCourseMaterialById(id, courseMaterial), HttpStatus.OK);
    }

    @DeleteMapping("/course_materials")
    public ResponseEntity<?> deleteAllCourseMaterials() {
        LOGGER.info("Inside deleteAllCourseMaterials() of CourseMaterialController");
        courseMaterialService.deleteAllCourseMaterials();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/course_materials/{id}")
    public ResponseEntity<?> deleteCourseMaterialById(@PathVariable(value = "id") Long id) throws CourseMaterialNotFoundException {
        LOGGER.info("Inside deleteStudentById() of CourseMaterialController" +
                "With PathVariable id=" + id);
        courseMaterialService.deleteCourseMaterialById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
