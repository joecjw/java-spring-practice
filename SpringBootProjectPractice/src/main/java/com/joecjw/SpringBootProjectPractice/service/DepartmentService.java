package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import java.util.List;

public interface DepartmentService {
    public Department saveDepartmentWithNoTeachers(Department department);

    public List<Department> getAllDepartments() throws DepartmentNotFoundException;

    public void deleteAllDepartments();

    public Department updateDepartmentById(Long id, Department department) throws DepartmentNotFoundException;

    public Department getDepartmentById(Long id) throws DepartmentNotFoundException;

    public void deleteDepartmentById(Long id) throws DepartmentNotFoundException;

    public Department getDepartmentByNameIgnoreCase(String name) throws DepartmentNotFoundException;

}
