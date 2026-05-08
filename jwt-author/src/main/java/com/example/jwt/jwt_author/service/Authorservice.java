package com.example.jwt.jwt_author.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.jwt_author.model.User;
import com.example.jwt.jwt_author.repo.AuthorRepo;


@Service
public class Authorservice {
    Logger logger=LoggerFactory.getLogger(Authorservice.class);
    @Autowired
    private AuthorRepo repo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerToPortal(User user){
        logger.info("Registering user with email: {}", user.getEmail());
        User userSaved=new User();
        userSaved.setEmail(user.getEmail());
        userSaved.setPassword(passwordEncoder.encode(user.getPassword()));
        userSaved.setRoles(user.getRoles());
        User newSaved=this.repo.save(userSaved);
        logger.info("User registered successfully with email: {}", user.getEmail());
        return newSaved;
        
    }
    

}
