//package com.proyectofinal.configuracion;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().anyRequest().permitAll(); // permite todo temporalmente
//	}
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable()) // Desactiva CSRF
//				.authorizeHttpRequests(auth -> auth
//						// .requestMatchers("/h2-console/**").permitAll() // Permitir acceso a H2
//						// Console
//						.requestMatchers(HttpMethod.POST, "/api/**").permitAll() // Permitir registro de usuario
//						.requestMatchers(HttpMethod.POST, "/api/**").permitAll() // Permitir login
//						.requestMatchers(HttpMethod.POST, "/api/**").permitAll() // Permitir login
//						.requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Permitir acceso a usuarios
//						.anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
//				).headers(headers -> headers.frameOptions(frame -> frame.disable()) // Permitir el uso de frames
//																					// (necesario para H2)
//				);
//
//		return http.build();
//	}
//
//	@Bean
//	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//		AuthenticationManagerBuilder authenticationManagerBuilder = http
//				.getSharedObject(AuthenticationManagerBuilder.class);
//		authenticationManagerBuilder.inMemoryAuthentication().withUser("user")
//				.password(passwordEncoder().encode("password")).roles("USER").and().withUser("admin")
//				.password(passwordEncoder().encode("admin")).roles("ADMIN");
//		return authenticationManagerBuilder.build();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}