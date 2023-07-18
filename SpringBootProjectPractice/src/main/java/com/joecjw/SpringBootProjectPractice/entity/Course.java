package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @NotEmpty(message = "The Course Name can not be empty")
    @Length(min = 1, max = 256, message = "The length of the course name should be between 1 to 256 characters")
    @Column(name = "course_name")
    private String courseName;

    @Digits(message = "Please set the credit to a valid integer value", integer = 1, fraction = 0)
    @Max(value = 9, message = "The maximum credit of a course is 9")
    @Min(value = 1, message = "The minimum credit of a course is 1")
    @Column(name = "course_credit")
    private Integer courseCredit;

    //OneToOne bidirectional mapping with CourseMaterial Entity
    //using a foreign key 'course_material_id' in 'course' Table
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "course_material_id")
    @Valid
    private CourseMaterial courseMaterial;

    @JsonIgnore
    @ManyToMany(
            mappedBy = "courses",
            fetch = FetchType.LAZY
    )
    private List<Student> students = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(
            mappedBy = "courses",
            fetch = FetchType.LAZY
    )
    private List<Teacher> teachers = new ArrayList<>();

    public void addStudent(Student student){
        if(!this.students.contains(student)){
            this.students.add(student);
        }
    }

    public void removeStudent(Student student){
        if(this.students.contains(student)){
            this.students.remove(student);
        }
    }
    public void removeAllStudents(){
        this.students.clear();
    }

    public void addTeacher(Teacher teacher){
        if(!this.teachers.contains(teacher)){
            this.teachers.add(teacher);
        }
    }

    public void removeTeacher(Teacher teacher){
        if(this.students.contains(teacher)){
            this.students.remove(teacher);
        }
    }

    public void removeAllTeachers(){
        this.teachers.clear();
    }


}
