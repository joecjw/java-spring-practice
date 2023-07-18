package com.joecjw.SpringBootProjectPractice.controller;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentId(1L)
                .name("HUMA")
                .address("Academic Building")
                .code("HUMA-00")
                .build();
    }

    @Test
    void getDepartmentById() throws Exception {
        Mockito.when(departmentService.getDepartmentById(1L))
                .thenReturn(department);
        mockMvc.perform(get("/api/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value(department.getName()))
                .andExpect(jsonPath("$.address").value(department.getAddress()))
                .andExpect(jsonPath("$.code").value(department.getCode()))
                .andExpect(jsonPath("$.id").value(department.getDepartmentId()));
    }

    @Test
    void saveDepartment() throws Exception {
        Department input = Department.builder()
                .name("HUMA")
                .address("Academic Building")
                .code("HUMA-00")
                .build();

        Mockito.when(departmentService.saveDepartmentWithNoTeachers(input))
                .thenReturn(department);
        mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"+
                        "\t\"name\":\"HUMA\",\n"+
                        "\t\"address\":\"Academic Building\",\n"+
                        "\t\"code\":\"HUMA-00\"\n"+
                        "}"))
                .andExpect(status().isCreated());
    }
}