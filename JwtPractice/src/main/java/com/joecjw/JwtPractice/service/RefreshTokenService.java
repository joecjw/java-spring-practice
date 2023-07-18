package com.joecjw.JwtPractice.service;

import com.joecjw.JwtPractice.auth.JwtResponse;
import com.joecjw.JwtPractice.entity.RefreshToken;

public interface RefreshTokenService {
    JwtResponse getNewTokenFromRefreshToken(String requestRefreshToken);

    RefreshToken createRefreshToken(Long userId);

    int deleteByUserId(Long userId);
}
