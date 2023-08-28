package com.joecjw.JwtPractice.auth;

import com.joecjw.JwtPractice.exception.InvalidUsernameException;
import com.joecjw.JwtPractice.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final  AuthenticationService authenticationService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest request){
        try{
           return new ResponseEntity(authenticationService.login(request), HttpStatus.OK);
        }catch (InvalidUsernameException e){
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            response.put("error", "Unauthorized");
            response.put("message", e.getMessage());
            response.put("path", "/login");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody @Valid TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return new ResponseEntity<>(refreshTokenService.getNewTokenFromRefreshToken(requestRefreshToken), HttpStatus.OK);
    }


}
