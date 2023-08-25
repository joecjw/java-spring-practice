package com.joecjw.identityservice.payload;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String jwtToken;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;
}