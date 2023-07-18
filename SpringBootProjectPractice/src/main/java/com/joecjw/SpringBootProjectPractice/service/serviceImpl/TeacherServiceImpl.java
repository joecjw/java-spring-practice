package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.entity.Student;
import com.joecjw.SpringBootProjectPractice.entity.Teacher;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.TeacherNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.CourseRepository;
import com.joecjw.SpringBootProjectPractice.repository.DepartmentRepository;
import com.joecjw.SpringBootProjectPractice.repository.TeacherRepository;
import com.joecjw.SpringBootProjectPractice.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;

    private DepartmentRepository departmentRepository;

    private CourseRepository courseRepository;

    @Override
    public List<Teacher> getAllTeachers() throws TeacherNotFoundException {
        List<Teacher> teacherList = new ArrayList<>();
        teacherRepository.findAll().forEach(teacherList::add);
        if(teacherList.isEmpty()){
            throw new TeacherNotFoundException("There Is No Teacher Registered yet");
        }else {
            return teacherList;
        }
    }

    @Override
    public Teacher getTeacherById(Long id) throws TeacherNotFoundException {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent()) {
            throw new TeacherNotFoundException("There is No Teacher with teacher id="+id);
        }else {
            return teacher.get();
        }
    }

    @Override
    public Teacher updateTeacherById(Long id, Teacher teacher) throws TeacherNotFoundException {
        Optional<Teacher> _teacher = teacherRepository.findById(id);
        if (!_teacher.isPresent()) {
            throw new TeacherNotFoundException("There is No Teacher with teacher id="+id);
        }else {
            _teacher.get().setFirstName(teacher.getFirstName());
            _teacher.get().setLastName(teacher.getLastName());
            _teacher.get().setEmail(teacher.getEmail());
            _teacher.get().setOnboardYear(teacher.getOnboardYear());
            return teacherRepository.save(_teacher.get());
        }
    }

    @Override
    public void deleteAllTeachers() {
        departmentRepository.findAll().forEach(Department::removeAllTeachers);
        departmentRepository.saveAll(departmentRepository.findAll());
        courseRepository.findAll().forEach(Course::removeAllTeachers);
        teacherRepository.deleteAll();
    }

    @Override
    public void deleteTeacherById(Long id) throws TeacherNotFoundException {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("There is No Teacher with teacher id="+id);
        }else {
            Teacher teacher = teacherRepository.findById(id).get();

            Department department = teacher.getDepartment();
            department.removeTeacher(teacher);
            departmentRepository.save(department);

            teacher.getCourses().forEach(course -> {
                course.removeTeacher(teacher);
            });
            teacherRepository.deleteById(id);
        }
    }

    @Override
    public String addTeacherToCourse(Long teacherId, Long courseId) throws CourseNotFoundException, TeacherNotFoundException {
        if(teacherRepository.existsById(teacherId)){
            if(courseRepository.existsById(courseId)){
                Teacher teacher = teacherRepository.findById(teacherId).get();
                Course course = courseRepository.findById(courseId).get();
                teacher.addCourse(course);
                course.addTeacher(teacher);
                teacherRepository.save(teacher);
                if ((teacher.getCourses().contains(course)) && (course.getStudents().contains(teacher))){
                    return "Teacher " + teacher.getFirstName() + teacher.getLastName() +
                            " is successfully ADDED To Course: " + course.getCourseName();
                }else {
                    return null;
                }
            }else {
                throw new CourseNotFoundException("Course with id= " + courseId +" is Not Found");
            }
        }else{
            throw new TeacherNotFoundException("Teacher with id= " + teacherId +" is Not Found");
        }
    }

    @Override
    public String removeTeacherFromCourse(Long teacherId, Long courseId) throws CourseNotFoundException, TeacherNotFoundException {
        if(teacherRepository.existsById(teacherId)){
            if(courseRepository.existsById(courseId)){
                Teacher teacher = teacherRepository.findById(teacherId).get();
                Course course = courseRepository.findById(courseId).get();
                teacher.removeCourse(course);
                course.removeTeacher(teacher);
                teacherRepository.save(teacher);
                if (!(teacher.getCourses().contains(course)) && !(course.getStudents().contains(teacher))){
                    return "Teacher " + teacher.getFirstName() + teacher.getLastName() +
                            " is successfully REMOVED From Course: " + course.getCourseName();
                }else {
                    return null;
                }
            }else {
                throw new CourseNotFoundException("Course with id= " + courseId +" is Not Found");
            }
        }else{
            throw new TeacherNotFoundException("Teacher with id= " + teacherId +" is Not Found");
        }
    }
}
