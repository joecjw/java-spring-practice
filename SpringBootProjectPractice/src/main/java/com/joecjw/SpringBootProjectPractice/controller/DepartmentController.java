package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.service.DepartmentService;
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
public class DepartmentController {

    private DepartmentService departmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping("/departments")
    public ResponseEntity<?> getAllDepartments() throws DepartmentNotFoundException {
        LOGGER.info("Inside getAllDepartments() of DepartmentController");
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.FOUND);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable(name = "id") Long id) throws DepartmentNotFoundException {
        LOGGER.info("Inside getDepartmentById() of DepartmentController," +
                "With PathVariable id=" + id);
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.FOUND);
    }

    @GetMapping("/departments/")
    public ResponseEntity<?> getDepartmentByName(@RequestParam(name = "name") String name) throws DepartmentNotFoundException {
        LOGGER.info("Inside getDepartmentByName() of DepartmentController," +
                "With RequestParameter name=" + name);
        return new ResponseEntity<>(departmentService.getDepartmentByNameIgnoreCase(name), HttpStatus.FOUND);
    }

    @PostMapping("/departments")
    public ResponseEntity<?> saveDepartmentWithNoTeachers(@Valid@RequestBody Department department) {
        LOGGER.info("Inside saveDepartmentWithNoTeachers() of DepartmentController" +
                "With Validated RequestBody department=" + department);
        return new ResponseEntity<>(departmentService.saveDepartmentWithNoTeachers(department), HttpStatus.CREATED);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<?> updateDepartmentById(@PathVariable(value = "id") Long id,
                                                  @Valid@RequestBody Department department) throws DepartmentNotFoundException {
        LOGGER.info("Inside updateDepartmentById() of DepartmentController" +
                "With PathVariable id=" + id + " and Validated RequestBody department=" + department);
        return new ResponseEntity<>(departmentService.updateDepartmentById(id, department), HttpStatus.OK);
    }

    @DeleteMapping("/departments")
    public ResponseEntity<?> deleteAllDepartments() {
        LOGGER.info("Inside deleteAllDepartments() of DepartmentController");
        departmentService.deleteAllDepartments();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable(value = "id") Long id) throws DepartmentNotFoundException {
        LOGGER.info("Inside deleteDepartmentById() of DepartmentController" +
                "With PathVariable id=" + id);
        departmentService.deleteDepartmentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
