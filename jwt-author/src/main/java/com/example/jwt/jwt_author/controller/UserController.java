package com.example.jwt.jwt_author.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @GetMapping("v1/home")
    public String home(){
        return "Welcome to the User Home Page!";
    }
     @GetMapping("v2/home")
    public String home2(){
        return "Welcome to the User Home Page!";
    }
    
}
