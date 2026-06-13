package com.example.demo1.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo1.model.AuthDB;
import com.example.demo1.repo.AuthRepo;
import com.example.demo1.util.JwtUtil;


@Service
public class AuthService {
	private static final Logger logger=LoggerFactory.getLogger(AuthService.class);
	public AuthService(com.example.demo1.repo.AuthRepo repo, com.example.demo1.util.JwtUtil jwtUtil, org.springframework.security.crypto.password.PasswordEncoder encoder, org.springframework.security.authentication.AuthenticationManager authManager) {
            this.repo = repo;
            this.jwtUtil = jwtUtil;
            this.encoder = encoder;
            this.authManager = authManager;
	}

	private final AuthRepo repo;
	

	private final JwtUtil jwtUtil;
	
	private final PasswordEncoder encoder;
	
	private final AuthenticationManager authManager;
    
	// @Autowired
	// private KafkaTemplate<String, Object> kafkaTemplate;
	
	
	public AuthDB  saveAuthDetails(AuthDB auth) {
		if (repo.findByEmail(auth.getEmail()).isPresent()) {
        throw new RuntimeException("Email already exists");
    }

    AuthDB data = new AuthDB();
    data.setEmail(auth.getEmail());
    data.setPassword(encoder.encode(auth.getPassword()));
    data.setRole(auth.getRole());
	data.setUsername(auth.getUsername());

    try {
        AuthDB savedAuth = repo.save(data);
		//this.kafkaTemplate.send("new-user-registered", savedAuth);
        return savedAuth;

    } catch (DataIntegrityViolationException e) {
        throw new RuntimeException("Email already exists");
    }

	}
	
	public String getEmail(int patient_id) {
		return this.repo.getEmailByPatientId(patient_id);
	}
	public String login(AuthDB auth) {
		logger.info("Login attempt for email: {}", auth.getEmail());
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
					System.out.println("role from service class"+auth.getRole());
	    
	    String token = jwtUtil.generateToken(auth.getEmail());
	    return token;
	    
	}
	

}
