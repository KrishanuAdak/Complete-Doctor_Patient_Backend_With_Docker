package com.example.demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo1.Exception.CustomUnauthorizationException;
import com.example.demo1.filter.JwtFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class DoctorConfig {
	private final JwtFilter jwtFilter;

	public DoctorConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/api/**") // Adjust path as needed
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

	@Bean
	public SecurityFilterChain secureRequests(HttpSecurity http) throws Exception {
		http
		.csrf().disable().authorizeHttpRequests(auth-> auth
			.requestMatchers("/doctor/verified-doctor/counts","/doctor/feign/details/**","/doctor/schedule/fetch/**")
			.permitAll()
	    .anyRequest().authenticated())
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling(ex-> ex.accessDeniedHandler(new CustomUnauthorizationException()));
		return http.build();
	}

	
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory factory) throws Exception {
		RedisTemplate redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		// redisTemplate.setKeySerializer(new RedisSerializer());
		// redisTemplate.setValueSerializer(new RedisSerializer());
		return redisTemplate;

	}
	
}
