package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import com.joecjw.SpringBootProjectPractice.entity.CourseMaterial;
import com.joecjw.SpringBootProjectPractice.entity.Student;
import com.joecjw.SpringBootProjectPractice.entity.Teacher;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.CourseRepository;
import com.joecjw.SpringBootProjectPractice.repository.StudentRepository;
import com.joecjw.SpringBootProjectPractice.repository.TeacherRepository;
import com.joecjw.SpringBootProjectPractice.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    private TeacherRepository teacherRepository;

    @Override
    public List<Course> getAllCourses() throws CourseNotFoundException {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findAll().forEach(courseList::add);
        if(courseList.isEmpty()){
            throw new CourseNotFoundException("There Is No Course created yet");
        }else {
            return courseList;
        }
    }

    @Override
    public Course getCourseById(Long id) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            throw new CourseNotFoundException("There is No Course with course id="+id);
        }else {
            return course.get();
        }
    }

    @Override
    public List<Course> getCoursesByStudentId(Long studentId) throws StudentNotFoundException {
        if(studentRepository.existsById(studentId)){
            return studentRepository.findById(studentId).get().getCourses();
        }else {
            throw new StudentNotFoundException("Student with id= " + studentId + " is Not Found");
        }
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course saveCourseWithCourseDetail(Course course) {
        Course _course = new Course();
        CourseMaterial _courseMaterial = new CourseMaterial();

        _course.setCourseName(course.getCourseName());
        _course.setCourseCredit(course.getCourseCredit());
        _course.setCourseMaterial(_courseMaterial);

        _courseMaterial.setUrl(course.getCourseMaterial().getUrl());
        _courseMaterial.setCourse(_course);

        return courseRepository.save(course);
    }

    @Override
    public Course updateCourseById(Long id, Course course) throws CourseNotFoundException {
        Optional<Course> _course = courseRepository.findById(id);
        if (!_course.isPresent()) {
            throw new CourseNotFoundException("There is No Course with course id="+id);
        }else {
            _course.get().setCourseName(course.getCourseName());
            _course.get().setCourseCredit(course.getCourseCredit());
            return courseRepository.save(_course.get());
        }
    }

    @Override
    public void deleteAllCourse() {
        studentRepository.findAll().forEach(Student::removeAllCourses);
        teacherRepository.findAll().forEach(Teacher::removeAllCourses);
        courseRepository.deleteAll();
    }

    @Override
    public void deleteCourseById(Long id) throws CourseNotFoundException {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("There is No Course with course id= "+id);
        }else {
            Course course = courseRepository.findById(id).get();
            course.getStudents().forEach(student -> {
                student.removeCourse(course);
            });
            course.getTeachers().forEach(teacher -> {
                teacher.removeCourse(course);
            });
            courseRepository.deleteById(id);
        }
    }

}
