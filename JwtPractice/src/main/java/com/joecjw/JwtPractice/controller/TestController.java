package com.joecjw.JwtPractice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String publicResources(){
        return "Public Resources";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public String userResources(){
        return "User Resources";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public String adminResources(){
        return "Admin Resources";
    }
}
