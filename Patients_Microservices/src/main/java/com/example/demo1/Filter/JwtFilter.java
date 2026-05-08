package com.example.demo1.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo1.config.UserDetailsServiceImpl;
import com.example.demo1.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class JwtFilter {
//extends OncePerRequestFilter{
//	@Autowired
//	private JwtUtil jwtUtil;
//	
//	@Autowired
//	private UserDetailsServiceImpl userDetailsImpl;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String header=request.getHeader("Authorization");
//		String patient_Email=null;
//		String jwt=null;
//		StringBuffer path=request.getRequestURL();
////		 if(path.st) {
////			 filterChain.doFilter(request, response);
////			 return;
////		 }
//		 
//	
//		 if(header!=null && header.startsWith("Bearer ")) {
//			 jwt=header.substring(7);
//			 patient_Email=jwtUtil.extractPatientEmail(jwt);
//		 }
//	
//		 if(patient_Email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//			UserDetails userDetails=userDetailsImpl.loadUserByUsername(patient_Email);
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetails.getAuthorities());
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//		
//	
//    
//			
//			 
//		 }
//		 filterChain.doFilter(request, response);
//	
//		
//	}

}
