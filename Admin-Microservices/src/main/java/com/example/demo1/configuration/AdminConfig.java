package com.example.demo1.configuration;

import org.springframework.context.annotation.Configuration;
 

@Configuration
//@EnableWebSecurity 
public class AdminConfig {

	
//	@Autowired
//	private JwtFilter jwtFilter;
//	
//	@Autowired 
//	private UserDetailsImpl userDetails;
//	
//	@Bean
//	public SecurityFilterChain secureHttpRequests(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.authorizeHttpRequests(auth->auth.requestMatchers("/admin/login","/admin/register","/actuator/info","/actuator/health").permitAll()
//				.anyRequest().authenticated())
//		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	}
	
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//		
//	}
//	
//	@Bean
//	public AuthenticationProvider authProvider() {
//		DaoAuthenticationProvider auth=new DaoAuthenticationProvider();
//		auth.setUserDetailsService(userDetails);
//		auth.setPasswordEncoder(passwordEncoder());
//		return auth;
//		
//	}
//	
//	@Bean
//	public AuthenticationManager configurationManager(AuthenticationConfiguration configuration) throws Exception {
//		return configuration.getAuthenticationManager();
//	}

}
