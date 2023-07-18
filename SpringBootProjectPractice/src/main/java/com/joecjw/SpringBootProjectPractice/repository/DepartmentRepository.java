package com.joecjw.SpringBootProjectPractice.repository;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByNameIgnoreCase(String name);
}
