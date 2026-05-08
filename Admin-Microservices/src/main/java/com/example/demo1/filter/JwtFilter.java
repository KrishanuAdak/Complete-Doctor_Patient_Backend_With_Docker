package com.example.demo1.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo1.userdetails.UserDetailsImpl;
import com.example.demo1.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class JwtFilter {
//extends OncePerRequestFilter
//{
//	@Autowired
//	private JwtUtil jwtUtil;
//	
//	@Autowired
//	private UserDetailsImpl userDetails;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		String path=request.getRequestURI();
//		
//		if(path.startsWith("/actuator")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		String header=request.getHeader("Authorization");
//		String email=null;
//		String jwt=null;
//		if(header!=null && header.startsWith("Bearer ")) {
//			jwt=header.substring(7);
//			email=jwtUtil.extractEmail(jwt);
//		}
//		if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//			UserDetails userAuth=userDetails.loadUserByUsername(email);
//			
//			if(jwtUtil.isValidateToken(jwt)) {
//				UsernamePasswordAuthenticationToken authDetails=new UsernamePasswordAuthenticationToken(userAuth,null,userAuth.getAuthorities());
//				authDetails.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authDetails);
//			}
//		}
//		filterChain.doFilter(request, response);
//	}

}
