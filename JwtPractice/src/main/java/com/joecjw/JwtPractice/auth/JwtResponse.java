package com.joecjw.JwtPractice.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private Long userId;
    private String jwtToken;
    private String type = "Bearer";
    private String refreshToken;
    private String username;
    private String email;
    private List<String> roles;
}
