package com.joecjw.identityservice.controller;

import com.joecjw.identityservice.payload.LoginRequest;
import com.joecjw.identityservice.payload.RegisterRequest;
import com.joecjw.identityservice.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        return new ResponseEntity(authenticationService.login(request), HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HashMap<String, Object> userDetails = new HashMap<>();
        userDetails.put("User Details", authentication.getPrincipal());
        userDetails.put("Roles", authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }
}