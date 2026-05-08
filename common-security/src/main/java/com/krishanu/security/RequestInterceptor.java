package com.krishanu.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        final String EXPECTED_SECRET_KEY = "irbbrberb433vhvxv3Q@s2g293hvbvjsSA22f3WQjiehIyee";

        // Read headers from Gateway
        String userId = request.getHeader("X-User-Id");
        String role = request.getHeader("X-User-Role");
        String username = request.getHeader("X-User-Name");
        String secret_key = request.getHeader("X-Secret-Key");
        System.out.println("From Common security -- " + userId + " " + role + " " + secret_key);
        String path = request.getRequestURI();
        if (path.startsWith("/appointment/v1/appointments/count") || path.contains("/verified-doctor/counts")) {
            System.out.println("Skipping authentication for path: " + path);
            return true;
        } 
        if (secret_key == null || userId == null || role == null || !secret_key.equals(EXPECTED_SECRET_KEY)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;

        }

        // Store in ThreadLocal
        RequestContext.setUserId(userId);
        RequestContext.setRole(role);
        RequestContext.setUsername(username);
        RequestContext.setSecretKey(secret_key);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        // prevent memory leak
        RequestContext.clear();
    }

   @Bean
public org.springframework.web.filter.CorsFilter corsFilter() {

    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();

    config.setAllowCredentials(true);
    config.setAllowedOrigins(List.of("http://localhost:4200"));
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
            new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return new org.springframework.web.filter.CorsFilter(source);
}

}
