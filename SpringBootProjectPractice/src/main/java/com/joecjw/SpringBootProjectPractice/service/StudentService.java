package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.Student;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents() throws StudentNotFoundException;

    Student getStudentById(Long id) throws StudentNotFoundException;

    List<Student> getStudentsByCourseId(Long courseId) throws CourseNotFoundException;

    Student updateStudentById(Long id, Student student) throws StudentNotFoundException;

    void deleteAllStudents();

    void deleteStudentById(Long id) throws StudentNotFoundException;

    String addStudentToCourse(Long studentId, Long courseId) throws StudentNotFoundException, CourseNotFoundException;

    String removeStudentFromCourse(Long studentId, Long courseId) throws StudentNotFoundException, CourseNotFoundException;


}
