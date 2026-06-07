package com.example.demo1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; // Spring Boot 3.x
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.model.AuthDB;
import com.example.demo1.service.AuthService;
import com.example.demo1.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth-service")
public class AuthController {
	@Autowired
	private AuthService service;
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/test")
	public String test() {
		return "Auth Service is up and running!!";
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveDetails(@Valid @RequestBody AuthDB auth, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return ResponseEntity.badRequest().body("Invalid data! Please check your input!!");
			}
			AuthDB data = this.service.saveAuthDetails(auth);
			return ResponseEntity.status(HttpStatus.CREATED).body(data);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Registration Failed! Please try again!!");
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDB auth, HttpServletResponse response) {
		try {
			String token = this.service.login(auth);
			Cookie cookie = new Cookie("jwt", token);
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setMaxAge(24 * 60 * 60);
			cookie.setPath("/");
			response.addCookie(cookie); // attach to response
			Map<String, Object> res = new HashMap<>();
			res.put("token", "Token Login Success!!");
			res.put("status", HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Login  failed!!");
		}

	}
	@GetMapping("/validate")
public ResponseEntity<Boolean> validate(HttpServletRequest request) {
    try {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);

        if (jwtUtil.isTokenValidated(token)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}

	@GetMapping("/patient/{id}")
	public String getEmailById(@PathVariable("id") int patient_id) {
		return this.service.getEmail(patient_id);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("jwt", "");
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(0); 
		cookie.setSecure(true); // Important!
		cookie.setDomain("api.appintment-easy-bengal.in");

		response.addCookie(cookie);
		return ResponseEntity.ok(Map.of("message", "Logged out"));
	}

}
