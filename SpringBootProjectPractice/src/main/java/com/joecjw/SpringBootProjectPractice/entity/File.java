package com.joecjw.SpringBootProjectPractice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @NotBlank(message = "The file's name cannot be blank")
    @Column(name = "file_name")
    private String name;

    @NotBlank(message = "The file's type cannot be blank")
    @Column(name = "file_type")
    private String type;

    @Lob
    @NotNull(message = "The file's content cannot be null")
    @Column(name = "file_data", length =  2147483647 )
    private byte[] data;

    @Digits(integer = Integer.MAX_VALUE, fraction = Integer.MAX_VALUE)
    @Column(name = "file_size")
    private long size;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "lastModified_time")
    private LocalDateTime lastModifiedTime;
}
