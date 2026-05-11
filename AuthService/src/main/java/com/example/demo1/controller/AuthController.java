package com.example.demo1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.model.AuthDB;
import com.example.demo1.service.AuthService;

@RestController
@RequestMapping("/auth-service")
public class AuthController {
	@Autowired
	private AuthService service;

	@Value("${server.port}")
	private String port;

	@GetMapping("/test")
	public String test() {
		return "Tested";
	}

	@GetMapping("/port")
	public String getPort() {
		// return "Pir";
		System.out.println("Port -- " + port);
		return "Port running on " + port;
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveDetails(@RequestBody AuthDB auth) {
		AuthDB data = this.service.saveAuthDetails(auth);
		try {
			if (data != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(data);
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Registration Failed! Please try again!!");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Failed! Please try again!!");

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDB auth) {
		try {
			String token = this.service.login(auth);
			Map<String, Object> response = new HashMap<>();
			response.put("Token", token);
			response.put("Status", HttpStatus.OK.value());
			if (token.length() > 0 && token.startsWith("ey")) {
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Login  failed!!");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login Failed!!");

	}

	@GetMapping("/patient/{id}")
	public String getEmailById(@PathVariable("id") int patient_id) {
		return this.service.getEmail(patient_id);
	}

}
