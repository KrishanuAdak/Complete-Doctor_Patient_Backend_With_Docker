package com.example.demo1.configuration;

import java.util.List;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final User_Role_Based_Key_Resolver keyResolver;

        public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                        User_Role_Based_Key_Resolver keyResolver) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.keyResolver = keyResolver;
        }

        @Bean
        public RedisRateLimiter redisRateLimiter() {
                return new RedisRateLimiter(15, 25);
        }

        @Bean
        public CorsWebFilter corsFilter() {

                // ✅ Public config — for AI and other public routes (no credentials)
                CorsConfiguration publicConfig = new CorsConfiguration();
                publicConfig.setAllowCredentials(false);
                publicConfig.addAllowedOrigin("*");
                publicConfig.addAllowedHeader("*");
                publicConfig.addAllowedMethod("*");
                publicConfig.setMaxAge(3600L);

                // ✅ Private config — for authenticated routes (with credentials)
                CorsConfiguration privateConfig = new CorsConfiguration();
                privateConfig.setAllowCredentials(true);
                privateConfig.setAllowedOrigins(List.of(
                                "http://localhost:4200",
                                "https://appointment-easy-bengal.in"
                ));
                privateConfig.addAllowedHeader("*");
                privateConfig.addAllowedMethod("*");
                privateConfig.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

                // ✅ AI route first — more specific paths first
                source.registerCorsConfiguration("/ai/**", publicConfig);
                source.registerCorsConfiguration("/auth-service/**", publicConfig);
                source.registerCorsConfiguration("/notification/**", publicConfig);

                // ✅ Authenticated routes
                source.registerCorsConfiguration("/doctor/**", privateConfig);
                source.registerCorsConfiguration("/patients/**", privateConfig);
                source.registerCorsConfiguration("/admin/**", privateConfig);
                source.registerCorsConfiguration("/appointment/**", privateConfig);
                source.registerCorsConfiguration("/payments/**", privateConfig);

                return new CorsWebFilter(source);
        }

        @Bean
        public RouteLocator customRoutes(RouteLocatorBuilder builder) {
                return builder.routes()

                                // Doctor Service
                                .route("doctor-service", r -> r.path("/doctor/**")
                                                .filters(f -> f
                                                                .filter(jwtAuthenticationFilter)
                                                                .requestRateLimiter(c -> {
                                                                        c.setKeyResolver(keyResolver);
                                                                        c.setRateLimiter(redisRateLimiter());
                                                                }))
                                                .uri("lb://doctor-service"))

                                // Patient Service
                                .route("patients-service", r -> r.path("/patients/**")
                                                .filters(f -> f
                                                                .filter(jwtAuthenticationFilter)
                                                                .requestRateLimiter(c -> {
                                                                        c.setKeyResolver(keyResolver);
                                                                        c.setRateLimiter(redisRateLimiter());
                                                                }))
                                                .uri("lb://patients-service"))

                                // Admin Service
                                .route("admin-service", r -> r.path("/admin/**")
                                                .filters(f -> f
                                                                .filter(jwtAuthenticationFilter))
                                                .uri("lb://admin-service"))

                                // Auth Service (public)
                                .route("auth-service", r -> r.path("/auth-service/**")
                                                .uri("lb://auth-service"))

                                // Appointment Service
                                .route("appointment-service", r -> r.path("/appointment/**")
                                                .filters(f -> f
                                                                .filter(jwtAuthenticationFilter)
                                                                .requestRateLimiter(c -> {
                                                                        c.setKeyResolver(keyResolver);
                                                                        c.setRateLimiter(redisRateLimiter());
                                                                }))
                                                .uri("lb://appointment-service"))

                                // Notification Service (public)
                                .route("notification-service", r -> r.path("/notification/**")
                                                .uri("lb://notification-service"))

                                // Payments Service
                                .route("payments-service", r -> r.path("/payments/**")
                                                .uri("lb://payments-service"))

                                // ✅ AI Service (public — no JWT, no rate limit, strip /ai prefix)
                                .route("ai-service", r -> r.path("/ai/**")
                                                .filters(f -> f.stripPrefix(1))
                                                .uri("lb://ai-service"))

                                .build();
        }
}