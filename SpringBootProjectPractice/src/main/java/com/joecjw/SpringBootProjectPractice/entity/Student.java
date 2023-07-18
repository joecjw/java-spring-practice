package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joecjw.SpringBootProjectPractice.utils.validator.ValidEntryYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@PrimaryKeyJoinColumn(name = "student_id")
@Table(name = "student")
public class Student extends User{

    @NotEmpty(message = "Student's programme can not be empty")
    @Column(name = "programme")
    private String programme;

    @NotNull(message = "Student's entry year can not be null")
    @ValidEntryYear
    @Column(name = "entry_year")
    private Integer entryYear;

//    ManyToMany bidirectional mapping with Student Entity
//    using a join table called 'student_course_map'
//    with columns 'course_id' and 'student_id'
    @JsonIgnore
    @ManyToMany(
            cascade = {
                CascadeType.MERGE,
                CascadeType.PERSIST
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "student_id"
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
