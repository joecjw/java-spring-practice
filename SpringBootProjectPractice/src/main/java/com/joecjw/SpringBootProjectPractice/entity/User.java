package com.joecjw.SpringBootProjectPractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.joecjw.SpringBootProjectPractice.entity.registration.PasswordResetToken;
import com.joecjw.SpringBootProjectPractice.entity.registration.VerificationToken;
import com.joecjw.SpringBootProjectPractice.utils.validator.ValidEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = User.class,name = "DEFAULT_USER"),
        @JsonSubTypes.Type(value = Student.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = Teacher.class, name = "TEACHER")

})
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email_address")
        }
)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "User's First Name can not be null")
    @NotEmpty(message = "User's First Name can not be Empty")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "User's Last Name can not be null")
    @NotEmpty(message = "User's Last Name can not be Empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "User's Email Address can not be null")
    @NotEmpty(message = "User's Email Address can not be Empty")
    @ValidEmail(message = "Please enter Email Address with correct format")
    @Column(name = "email_address")
    private String email;

    @NotNull(message = "User's password can not be null" )
    @NotEmpty(message = "User's password can not be Empty")
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active = false;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable( name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private VerificationToken verificationToken;

    @JsonIgnore
    @OneToOne(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private PasswordResetToken passwordResetToken;

}
