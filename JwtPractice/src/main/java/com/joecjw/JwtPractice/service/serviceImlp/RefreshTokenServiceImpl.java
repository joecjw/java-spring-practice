package com.joecjw.JwtPractice.service.serviceImlp;

import com.joecjw.JwtPractice.auth.JwtResponse;
import com.joecjw.JwtPractice.entity.RefreshToken;
import com.joecjw.JwtPractice.entity.User;
import com.joecjw.JwtPractice.exception.TokenRefreshException;
import com.joecjw.JwtPractice.repository.RefreshTokenRepository;
import com.joecjw.JwtPractice.repository.UserRepository;
import com.joecjw.JwtPractice.security.jwt.JwtService;
import com.joecjw.JwtPractice.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final Long refreshTokenDurationMs = 86400000L;

    private RefreshTokenRepository refreshTokenRepository;

    private UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findById(userId).get();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        userRepository.save(user);

        return refreshToken;
    }

    @Override
    public int deleteByUserId(Long userId) {
        return  refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    @Override
    public JwtResponse getNewTokenFromRefreshToken(String requestRefreshToken) {
        //find refresh token in DB
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new TokenRefreshException("Refresh token: " + requestRefreshToken +
                         " is not in database!"));

        //verify the found refresh token
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException("Refresh token: " + refreshToken.getToken()
                    + " was expired. Please make a new signin request");
        }

        //create new JwtToken
        User user = refreshToken.getUser();
        String newJwtToken = JwtService.generateJwtToken(user);
        List<String> roles = user
                .getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUserId(user.getId());
        jwtResponse.setType("Bearer");
        jwtResponse.setJwtToken(newJwtToken);
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setRoles(roles);

        return jwtResponse;
    }
}