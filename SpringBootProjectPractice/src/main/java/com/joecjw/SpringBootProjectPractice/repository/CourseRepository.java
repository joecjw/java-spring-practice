package com.joecjw.SpringBootProjectPractice.repository;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
