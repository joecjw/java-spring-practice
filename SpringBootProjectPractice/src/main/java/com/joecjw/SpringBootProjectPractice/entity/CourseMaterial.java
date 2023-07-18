package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_material")
public class CourseMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_material_id")
    private Long courseMaterialId;

    @NotNull(message = "The Course Material's URL can not be null")
    @Column(name = "url")
    private String url;

    //OneToOne bidirectional mapping with Course Entity
    //using a foreign key 'course_material_id' in 'course' Table
    @JsonIgnore
    @OneToOne(
            mappedBy = "courseMaterial",
            fetch = FetchType.LAZY,
            optional = false
    )
    @Valid
    private Course course;
}
