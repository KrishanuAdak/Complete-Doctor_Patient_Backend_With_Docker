package com.example.demo1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;           // ✅ new import
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${X-Secret-Key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = null;

        // ✅ Step 1: Read from HttpOnly Cookie
        HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("jwt");
        if (jwtCookie != null) {
            token = jwtCookie.getValue();
        }

        // ✅ Step 2: Fallback to Authorization header (Postman/testing)
        if (token == null) {
            String authHeader = exchange.getRequest().getHeaders()
                                        .getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("Path -"+path);
        if (path.startsWith("/appointment/v1/appointments/count") || path.contains("/verified-doctor/counts") || path.startsWith("/ai/chat")) {
            System.out.println("Skipping authentication for path: " + path);
            return chain.filter(exchange);
        }

        // ✅ Step 3: Reject if no token found
        if (token == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // ✅ Your existing validation logic — unchanged
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            int user_Id = claims.get("ID", Integer.class);
            String user_id_parsed = String.valueOf(user_Id);
            String subject = claims.getSubject();

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User-name", subject)
                    .header("X-User-Role", role)
                    .header("X-User-Id", user_id_parsed)
                    .header("X-Secret-Key", secretKey)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}