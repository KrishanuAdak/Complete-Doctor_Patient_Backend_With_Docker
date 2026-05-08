package com.example.demo1.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo1.filter.jwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private jwtRequestFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
             http.csrf().disable().authorizeHttpRequests(auth->
                auth.requestMatchers("/public/**", "/ws/**", "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/port/**","/appointment/v1/appointments/count/**").permitAll()
                .requestMatchers("/v1/schedule/**").hasRole("PATIENT")
                .anyRequest().authenticated()
              )
              .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
              .build();

    }

}  
