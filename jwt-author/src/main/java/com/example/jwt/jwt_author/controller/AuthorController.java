package com.example.jwt.jwt_author.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@PreAuthorize("hasRole('AUTHOR')")
public class AuthorController {
    @RequestMapping("/dashboard")
    public String authorDashboard(){
        return "Welcome to the Author Dashboard!";
    }
    

}
