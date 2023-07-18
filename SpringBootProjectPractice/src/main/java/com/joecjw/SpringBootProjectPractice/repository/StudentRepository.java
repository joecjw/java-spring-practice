package com.joecjw.SpringBootProjectPractice.repository;

import com.joecjw.SpringBootProjectPractice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /*
    JPQL Approach
    @Query("select s from Student s where s.emailId = ?1")
    public Optional<Student> getStudentByEmailId(String emailId);
     */

    /*
    Native Query Approach
    @Query(
        value = "select * from student s where s.email_id = ?1"
        nativeQuery = true
    )
    public Optional<Student> getStudentByEmailId(String emailId);
     */
}
