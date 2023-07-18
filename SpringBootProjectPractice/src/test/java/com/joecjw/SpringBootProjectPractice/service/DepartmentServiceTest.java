package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                                     .departmentId(1L)
                                     .name("IT-DEVELOPMENT")
                                     .address("Hong Kong")
                                     .code("IT-00")
                                     .build();
        Mockito.when(departmentRepository.findByNameIgnoreCase("IT-DEVELOPMENT"))
                                         .thenReturn(department);
    }

    @Test
    @DisplayName("Get Data based on Valid Department Name")
    public void whenValidDepartmentNameIgnoreCase_thenDepartmentShouldFound() throws DepartmentNotFoundException {
        String departmentName = "IT";
        Department resultDepartment = departmentService.getDepartmentByNameIgnoreCase(departmentName);
        assertEquals(departmentName, resultDepartment.getName());
    }
}