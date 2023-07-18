package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    @NotBlank(message = "The assignment's name cannot be blank")
    @Column(name = "assignment_name")
    private String name;

    @Lob
    @Column(name = "assignment_description", length = 2147483647)
    private String description;

    @CreationTimestamp
    @Column(name = "assignment_creationTime")
    private LocalDateTime creationTime;

    @Column(name = "assignment_ddl")
    private LocalDateTime deadLine;

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "assignment_id",
            referencedColumnName = "assignment_id"
    )
    @Valid
    private List<File> files = new ArrayList<>();

}
