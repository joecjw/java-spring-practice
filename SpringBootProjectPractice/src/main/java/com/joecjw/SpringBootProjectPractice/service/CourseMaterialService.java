package com.joecjw.SpringBootProjectPractice.service;

import com.joecjw.SpringBootProjectPractice.entity.CourseMaterial;
import com.joecjw.SpringBootProjectPractice.exception.CourseMaterialNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;

import java.util.List;

public interface CourseMaterialService {
    List<CourseMaterial> getAllCourseMaterials() throws CourseMaterialNotFoundException;

    CourseMaterial getCourseMaterialById(Long id) throws CourseMaterialNotFoundException;

    CourseMaterial saveCourseMaterialByCourseId(Long courseId, CourseMaterial courseMaterial) throws CourseNotFoundException;

    CourseMaterial updateCourseMaterialById(Long id, CourseMaterial courseMaterial) throws CourseMaterialNotFoundException;

    void deleteAllCourseMaterials();

    void deleteCourseMaterialById(Long id) throws CourseMaterialNotFoundException;
}
