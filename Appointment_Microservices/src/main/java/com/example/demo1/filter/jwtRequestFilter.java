package com.example.demo1.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
@Service
public class jwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path=request.getRequestURI();
            System.out.println("Request URI in filter -- " + path+" Dispatcher Type -- "+request.getDispatcherType());
            if(path.startsWith("/ws") || path.startsWith("/favicon.ico") || path.contains("/actuator") || path.contains("/v3/api-docs") || path.contains("/swagger-ui") || path.contains("/swagger-ui.html") ||
            path.startsWith("/appointment/v1/appointments/count")) {
                System.out.println("Skipping authentication for path: " + path);
                filterChain.doFilter(request, response);
                return;
            }
            String role = request.getHeader("X-User-Role");
            String userId = request.getHeader("X-User-Id");
            System.out.println("Role from once per request filter -- " + role);
            System.out.println("UserId from once per request filter -- " + userId);
            // if ((role == null || userId == null) &&  !path.contains("/appointment/v1/appointments/count")) {
            //     System.out.println("Missing headers: X-User-Role or X-User-Id");
            //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //     response.getWriter().write("Unauthorized : Missing Headers Details");
            //     return;
            // }
            role = role.toUpperCase();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
                    authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization failed.");
            return;
        }
        filterChain.doFilter(request, response);
    }

}
