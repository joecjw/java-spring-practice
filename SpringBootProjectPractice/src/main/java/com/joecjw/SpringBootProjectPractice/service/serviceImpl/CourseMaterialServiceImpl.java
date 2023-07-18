package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import com.joecjw.SpringBootProjectPractice.entity.CourseMaterial;
import com.joecjw.SpringBootProjectPractice.exception.CourseMaterialNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.CourseMaterialRepository;
import com.joecjw.SpringBootProjectPractice.repository.CourseRepository;
import com.joecjw.SpringBootProjectPractice.service.CourseMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseMaterialServiceImpl implements CourseMaterialService {

    private CourseRepository  courseRepository;

    private CourseMaterialRepository courseMaterialRepository;

    @Override
    public List<CourseMaterial> getAllCourseMaterials() throws CourseMaterialNotFoundException {
        List<CourseMaterial> courseMaterialsList = new ArrayList<>();
        courseMaterialRepository.findAll().forEach(courseMaterialsList::add);
        if(courseMaterialsList.isEmpty()){
            throw new CourseMaterialNotFoundException("There Is No Course Material created yet");
        }else {
            return courseMaterialsList;
        }
    }

    @Override
    public CourseMaterial getCourseMaterialById(Long id) throws CourseMaterialNotFoundException {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);
        if (!courseMaterial.isPresent()) {
            throw new CourseMaterialNotFoundException("There is No Course Material with course_material id="+id);
        }else {
            return courseMaterial.get();
        }
    }

    @Override
    public CourseMaterial saveCourseMaterialByCourseId(Long courseId, CourseMaterial courseMaterial) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            CourseMaterial _courseMaterial = new CourseMaterial();
            _courseMaterial.setUrl(courseMaterial.getUrl());
            _courseMaterial.setCourse(course.get());
            course.get().setCourseMaterial(_courseMaterial);
            courseRepository.save(course.get());
            return course.get().getCourseMaterial();
        }else {
            throw new CourseNotFoundException("Course with ID= " + courseId + " Not Found");
        }
    }

    @Override
    public CourseMaterial updateCourseMaterialById(Long id, CourseMaterial courseMaterial) throws CourseMaterialNotFoundException {
        Optional<CourseMaterial> _courseMaterial = courseMaterialRepository.findById(id);
        if (!_courseMaterial.isPresent()) {
            throw new CourseMaterialNotFoundException("There is No Course Material with course_material id="+id);
        }else {
            _courseMaterial.get().setUrl(courseMaterial.getUrl());
            return courseMaterialRepository.save(_courseMaterial.get());
        }
    }

    @Override
    public void deleteAllCourseMaterials() {
        courseMaterialRepository.deleteAll();
    }

    @Override
    public void deleteCourseMaterialById(Long id) throws CourseMaterialNotFoundException {
        if (!courseMaterialRepository.existsById(id)) {
            throw new CourseMaterialNotFoundException("There is No Course Material with course_material id="+id);
        }else {
            courseMaterialRepository.deleteById(id);
        }
    }
}
