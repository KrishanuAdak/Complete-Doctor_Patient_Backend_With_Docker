package com.example.demo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo1.Filter.JwtFilter;

//
//@Configuration

public class PatientsConfig {
//	
//	@Autowired
//	public JwtFilter jwtFilter;
//	
//	@Autowired
//	private UserDetailsServiceImpl serviceImpl;
//	
//	@Bean
//	public SecurityFilterChain httpRequests(HttpSecurity http) throws Exception
//	{
//		
//		http.csrf().disable().authorizeHttpRequests(
//				auth->auth.requestMatchers("patients/login/**","patients/register/**").permitAll()
//				.anyRequest().authenticated())
//		        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//		
//	}
//	
//	@Bean
//	public PasswordEncoder encodePasword() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	
//	@Bean
//	public AuthenticationManager authentication(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//		provider.setUserDetailsService(serviceImpl);
//		provider.setPasswordEncoder(encodePasword());
//		return provider;
//	}
//	
	


}
