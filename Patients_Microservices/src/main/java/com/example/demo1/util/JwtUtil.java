package com.example.demo1.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;

//@Component
public class JwtUtil {
//	private String secret_key="416332b41e0b77dc124e78c7e0f84224006421d51422f3fd6e9971cbbbcc5901afb9d365154b77d65b879a237c81d627ec7a3ca090776d7d10fb6ab35a63d609ad8c42e1a8deb97e0baf120e027db9610ada4f0a851166fbb3187d600caed6a4";
//	
//	private SecretKey getSigningKey() {
//		return Keys.hmacShaKeyFor(secret_key.getBytes());
//		
//	}
//	public String createToken(Map<String,Object> claims,String email) {
//	
//		return Jwts.builder()
//				.setClaims(claims)
//				.setSubject(email)
//				.setIssuedAt(new Date())
//				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*30))
//				.signWith(getSigningKey())
//				.compact();		
//	}
//	public String generateToken(String username) {
//		Map<String,Object> claims=new HashMap<String,Object>();
//		return createToken(claims,username);
//	}
//	public String extractPatientEmail(String token)
//	{
//		return extractAllClaims(token).getSubject();
//		
//		
//	}
//	
//
//	public Claims extractAllClaims(String token) {
//		
//		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//		
//	}
//	
//	private Date getExpiration(String token) {
//		return extractAllClaims(token).getExpiration();
//		
//	}
//	private Boolean isTokenExpired(String token) {
//		return getExpiration(token).before(new Date());
//		
//	}
//	public  boolean isValidateToken(String token) {
//		return !isTokenExpired(token);
//	}

}
