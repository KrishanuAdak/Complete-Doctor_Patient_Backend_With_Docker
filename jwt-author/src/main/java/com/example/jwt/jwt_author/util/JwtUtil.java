package com.example.jwt.jwt_author.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.jwt.jwt_author.model.User;
import com.example.jwt.jwt_author.repo.AuthorRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    @Value("${jwt.secret_key}")
    private  String SECRET_KEY;
    
    @Autowired
    private AuthorRepo repo;

    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        
    }
    public String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey())
                .compact();
    }
    public String generateToken(String subject){
        Map<String,Object> claims=new HashMap<>();
        User roles=this.repo.findByEmail(subject);
        claims.put("roles",roles.getRoles());
        return createToken(claims, subject);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        
    }
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }
    public boolean isExpiredToken(String token){
        return extractAllClaims(token).getExpiration().before(new Date());

    }
    public boolean isValidateToken(String token,UserDetails userDetails){
        String username=extractAllClaims(token).getSubject();
        return !isExpiredToken(token) && username.equalsIgnoreCase(userDetails.getUsername());
    }


    
}
