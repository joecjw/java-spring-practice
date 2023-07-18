package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.Course;
import com.joecjw.SpringBootProjectPractice.entity.CourseMaterial;
import com.joecjw.SpringBootProjectPractice.entity.Student;
import com.joecjw.SpringBootProjectPractice.exception.CourseNotFoundException;
import com.joecjw.SpringBootProjectPractice.exception.StudentNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.CourseRepository;
import com.joecjw.SpringBootProjectPractice.repository.StudentRepository;
import com.joecjw.SpringBootProjectPractice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private CourseRepository courseRepository;

    @Override
    public List<Student> getAllStudents() throws StudentNotFoundException {
       List<Student> studentList = new ArrayList<>();
       studentRepository.findAll().forEach(studentList::add);
       if(studentList.isEmpty()){
           throw new StudentNotFoundException("There Is No Student Registered yet");
       }else {
           return studentList;
       }
    }

    @Override
    public Student getStudentById(Long id) throws StudentNotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new StudentNotFoundException("There is No Student with student id="+id);
        }else {
            return student.get();
        }
    }

    @Override
    public List<Student> getStudentsByCourseId(Long courseId) throws CourseNotFoundException {
        if(courseRepository.existsById(courseId)){
            return courseRepository.findById(courseId).get().getStudents();
        }else {
            throw new CourseNotFoundException("Course with id= " + courseId + " is Not Found");
        }
    }

    @Override
    public Student updateStudentById(Long id, Student student) throws StudentNotFoundException {
        Optional<Student> _student = studentRepository.findById(id);
        if (!_student.isPresent()) {
            throw new StudentNotFoundException("There is No Student with student id="+id);
        }else {
            _student.get().setFirstName(student.getFirstName());
            _student.get().setLastName(student.getLastName());
            _student.get().setEmail(student.getEmail());
            _student.get().setProgramme(student.getProgramme());
            _student.get().setEntryYear(student.getEntryYear());
            return studentRepository.save(_student.get());
        }
    }

    @Override
    public void deleteAllStudents() {
        courseRepository.findAll().forEach(Course::removeAllStudents);
        studentRepository.deleteAll();
    }

    @Override
    public void deleteStudentById(Long id) throws StudentNotFoundException {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("There is No Student with student id="+id);
        }else {
            Student student = studentRepository.findById(id).get();
            student.getCourses().forEach(course -> {
                course.removeStudent(student);
            });
            studentRepository.deleteById(id);
        }
    }

    @Override
    public String addStudentToCourse(Long studentId, Long courseId) throws StudentNotFoundException, CourseNotFoundException {
        if(studentRepository.existsById(studentId)){
            if(courseRepository.existsById(courseId)){
                Student student = studentRepository.findById(studentId).get();
                Course course = courseRepository.findById(courseId).get();
                student.addCourse(course);
                course.addStudent(student);
                studentRepository.save(student);
                if ((student.getCourses().contains(course)) && (course.getStudents().contains(student))){
                    return "Student " + student.getFirstName() + student.getLastName() +
                            " is successfully ADDED To Course: " + course.getCourseName();
                }else {
                    return null;
                }
            }else {
                throw new CourseNotFoundException("Course with id= " + courseId +" is Not Found");
            }
        }else{
            throw new StudentNotFoundException("Student with id= " + studentId +" is Not Found");
        }
    }

    @Override
    public String removeStudentFromCourse(Long studentId, Long courseId) throws StudentNotFoundException, CourseNotFoundException {
        if(studentRepository.existsById(studentId)){
            if(courseRepository.existsById(courseId)){
                Student student = studentRepository.findById(studentId).get();
                Course course = courseRepository.findById(courseId).get();
                student.removeCourse(course);
                course.removeStudent(student);
                studentRepository.save(student);
                if (!(student.getCourses().contains(course)) && !(course.getStudents().contains(student))){
                    return "Student " + student.getFirstName() + student.getLastName() +
                            " is successfully REMOVED From Course: " + course.getCourseName();
                }else {
                    return null;
                }
            }else {
                throw new CourseNotFoundException("Course with id= " + courseId +" is Not Found");
            }
        }else{
            throw new StudentNotFoundException("Student with id= " + studentId +" is Not Found");
        }
    }


}
