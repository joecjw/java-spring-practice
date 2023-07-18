package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses() throws CourseNotFoundException;

    Course getCourseById(Long id) throws CourseNotFoundException;

    Course saveCourse(Course course);

    Course saveCourseWithCourseDetail(Course course);

    Course updateCourseById(Long id, Course course) throws CourseNotFoundException;

    void deleteAllCourse();

    void deleteCourseById(Long id) throws CourseNotFoundException;

    List<Course> getCoursesByStudentId(Long studentId) throws StudentNotFoundException;
}
