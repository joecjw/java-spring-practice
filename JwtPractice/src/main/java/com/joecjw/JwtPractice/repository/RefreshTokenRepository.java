package com.joecjw.JwtPractice.repository;

import com.joecjw.JwtPractice.entity.RefreshToken;
import com.joecjw.JwtPractice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);

}
