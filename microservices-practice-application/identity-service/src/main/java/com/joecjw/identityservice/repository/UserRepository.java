package com.joecjw.identityservice.repository;

import com.joecjw.identityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findAllByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
}
