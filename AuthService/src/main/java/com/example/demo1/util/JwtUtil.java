package com.example.demo1.util;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.demo1.repo.AuthRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final AuthRepo repo;
	
	private final String SECRET_KEY="hfihsdfiuifh8098nofhfhfih@hoihfohhkoolvhhncvbkhihdifheioheoe0fe0ehiheurjhfhfksnbsdnsdfsijfhihfsdjhbfsigfsfifsfsff";

	public JwtUtil(AuthRepo repo) {
		this.repo = repo;
	}
	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
	}
	public String generateToken(String email) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("role", this.repo.findByEmail(email).get().getRole());
		claims.put("ID",this.repo.findByEmail(email).get().getId());	
		System.out.println("Roles"+this.repo.findByEmail(email).get().getRole());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(getSigningKey())
				.compact();
		
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
		
	}
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Date TokenExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
	public boolean isTokenValidated(String token) {
		return !isTokenExpired(token);
	}
	
	

}
