package com.joecjw.JwtPractice.controller;

import com.joecjw.JwtPractice.entity.User;
import com.joecjw.JwtPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final  UserService userService;

    @PreAuthorize("hasRole('ROLE_TESTER') or hasRole('ROLE_USER')")
    @GetMapping("/user")
    ResponseEntity<?> getAllUsers(){
       ArrayList<User> users = userService.getAllUsers();
       return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
