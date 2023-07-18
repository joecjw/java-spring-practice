package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.Teacher;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.TeacherNotFoundException;
import java.util.List;

public interface TeacherService {
    List<Teacher> getAllTeachers() throws TeacherNotFoundException;

    Teacher getTeacherById(Long id) throws TeacherNotFoundException;

    Teacher updateTeacherById(Long id, Teacher teacher) throws TeacherNotFoundException;

    String addTeacherToCourse(Long teacherId, Long courseId) throws CourseNotFoundException, TeacherNotFoundException;

    String removeTeacherFromCourse(Long teacherId, Long courseId) throws CourseNotFoundException, TeacherNotFoundException;

    void deleteAllTeachers();

    void deleteTeacherById(Long id) throws TeacherNotFoundException;

}
