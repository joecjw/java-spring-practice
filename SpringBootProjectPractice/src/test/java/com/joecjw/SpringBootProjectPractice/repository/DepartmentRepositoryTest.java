package com.joecjw.SpringBootProjectPractice.repository;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {
//
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @BeforeEach
//    void setUp() {
//        Department department_1 = Department.builder()
//                .id(1L)
//                .name("IT")
//                .address("Hong Kong")
//                .code("IT-00")
//                .build();
//        Department department_2 = Department.builder()
//                .id(2L)
//                .name("CSE")
//                .address("Hong Kong")
//                .code("CSE-00")
//                .build();
//        entityManager.persist(department_1);
//        entityManager.persist(department_2);
//    }
//
//    @Test
//    @Disabled
//    @DisplayName("Get Data based on Valid Department Name")
//    public void whenFindByNameIgnoreCase_thenReturnListOfFoundDepartment() {
//        departmentRepository.findByNameIgnoreCase("IT")
//                            .forEach(result -> assertEquals("IT", result.getName()));
//    }
}