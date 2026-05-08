package com.example.jwt.jwt_author.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.jwt_author.model.User;
import com.example.jwt.jwt_author.service.Authorservice;
import com.example.jwt.jwt_author.util.JwtUtil;

@RestController
public class PublicController {
    @Autowired
    private Authorservice authorService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint accessible to everyone.";
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User registeredUser = authorService.registerToPortal(user);
        if(registeredUser == null){
            return ResponseEntity.badRequest().body("Registration failed");
        }
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String token=jwtUtil.generateToken(user.getEmail());
        Map<String,Object> res=new HashMap<>();
        res.put("Token",token);
        res.put("Status",HttpStatus.OK.value());
        return ResponseEntity.ok(res);
    }
    
}
