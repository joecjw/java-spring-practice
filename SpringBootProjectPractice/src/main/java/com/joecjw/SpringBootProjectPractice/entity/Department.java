package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @NotEmpty(message = "Please Add Department Name")
    @Length(min = 1, max = 60, message = "The Department Name should be between 1 to 60 characters")
    @Column(name = "department_name")
    private String name;

    @NotEmpty(message = "Please Add Department Address")
    @Length(min = 1, max = 200, message = "The Department Name should be between 1 to 200 characters")
    @Column(name = "department_address")
    private String address;

    @NotEmpty(message = "Please Add Department Code")
    @Length(min = 1, max = 30, message = "The Department Name should be between 1 to 30 characters")
    @Column(name = "department_code")
    private String code;

//    OneToMany unidirectional mapping with Department Entity
//    using a foreign key 'department_id' in 'teacher' Table
    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "department"
    )
    public List<Teacher> teachers = new ArrayList<>();

    public void removeTeacher(Teacher teacher){
        if(this.teachers.contains(teacher)){
            this.teachers.remove(teacher);
        }
    }

    public void removeAllTeachers(){
        teachers.clear();
    }

    public void addTeacher(Teacher teacher){
        if(!this.teachers.contains(teacher)){
            this.teachers.add(teacher);
        }
    }

}
