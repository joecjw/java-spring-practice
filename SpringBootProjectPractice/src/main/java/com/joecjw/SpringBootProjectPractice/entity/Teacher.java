package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joecjw.SpringBootProjectPractice.utils.validator.ValidEntryYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@PrimaryKeyJoinColumn(name = "teacher_id")
@Table(name = "teacher")
public class Teacher extends User{

    @NotNull(message = "Teacher's onboard year can not be null")
    @ValidEntryYear
    @Column(name = "onboard_year")
    private Integer onboardYear;


//    ManyToOne unidirectional mapping with Department Entity
//    using a foreign key 'department_id' in 'teacher' Table
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "department_id",
            referencedColumnName = "department_id"
    )
    private Department department;


    //ManyToMany bidirectional mapping with Course Entity
    //using a join table called 'teacher_course'
    //with columns 'teacher_id' and 'course_id'
    @JsonIgnore
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(
                    name = "teacher_id",
                    referencedColumnName = "teacher_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    referencedColumnName = "course_id"
            )
    )
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course){
        if(!this.courses.contains(course)){
            this.courses.add(course);
        }
    }

    public void removeCourse(Course course){
        if(this.courses.contains(course)){
            this.courses.remove(course);
        }
    }

    public void removeAllCourses(){
        this.courses.clear();
    }
}
