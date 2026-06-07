package com.example.demo1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Lazy;

import com.example.demo1.model.AuthDB;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.example.demo1.exception.AuthAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	@Lazy
	@Autowired
	private UserDetailsImpl userDetails;

	@Bean
	public RedisTemplate<String, AuthDB> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, AuthDB> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		Jackson2JsonRedisSerializer<AuthDB> serializer = new Jackson2JsonRedisSerializer<>(AuthDB.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mapper);

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain secureHttps(HttpSecurity http) throws Exception {
		try {
			http.csrf().disable().authorizeHttpRequests(auth -> auth.requestMatchers("/auth-service/**").permitAll()
					.anyRequest().authenticated())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.exceptionHandling(ex -> ex.accessDeniedHandler(new AuthAccessDeniedHandler()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetails);
		provider.setPasswordEncoder(encodePassword());
		return provider;
	}
	//

}
