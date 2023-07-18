package com.joecjw.Oauth2AuthorizationServer.repository;

import com.joecjw.Oauth2AuthorizationServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);
}
