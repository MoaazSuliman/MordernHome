package com.moaaz.modernhome.security;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


	@Bean
	public static PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001", "http://localhost:3002"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setExposedHeaders(Arrays.asList("X-Total-Count")); // Expose the custom header
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(request -> {

							request.requestMatchers("/auth/login").permitAll();
							request.requestMatchers("/v2/api-docs",
									"/v3/api-docs",
									"/v3/api-docs/**",
									"/swagger-resources",
									"/swagger-resources/**",
									"/configuration/ui",
									"/configuration/security",
									"/swagger-ui/**",
									"/webjars/**",
									"/swagger-ui.html").permitAll();


							request.requestMatchers(HttpMethod.POST, "/categories/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.PUT, "/categories/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.DELETE, "/categories/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.GET, "/categories/**").permitAll();

							request.requestMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN", "EMPLOYEE" , "USER");
							request.requestMatchers(HttpMethod.PUT, "/products/*").hasAnyRole("ADMIN", "EMPLOYEE");
					request.requestMatchers(HttpMethod.DELETE, "/products/*").hasAnyRole("ADMIN", "EMPLOYEE");
					request.requestMatchers(HttpMethod.POST, "/products/search").permitAll();
					request.requestMatchers(HttpMethod.GET, "/products/**").permitAll();



							request.requestMatchers(HttpMethod.POST, "/employees/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.PUT, "/employees/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.DELETE, "/employees/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.GET, "/employees/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER");

							request.requestMatchers(HttpMethod.GET, "/employeeLogs/**").hasAnyRole("ADMIN");

							request.requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("ADMIN", "EMPLOYEE" , "USER");
							request.requestMatchers(HttpMethod.PUT, "/orders/*").hasAnyRole("ADMIN", "EMPLOYEE", "USER");
							request.requestMatchers(HttpMethod.POST, "/orders/*/*").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.GET, "/orders").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers(HttpMethod.GET, "/orders/*/*").hasAnyRole("ADMIN", "EMPLOYEE", "USER");
							request.requestMatchers(HttpMethod.GET, "/orders/*").hasAnyRole("ADMIN", "EMPLOYEE", "USER");

							request.requestMatchers("/navbarImages").hasAnyRole("ADMIN");

							//users
							request.requestMatchers("/users").hasAnyRole("ADMIN", "EMPLOYEE");
							request.requestMatchers("/users/**").hasAnyRole("ADMIN", "EMPLOYEE");
							request.anyRequest().permitAll();
						}

				);

		http.csrf(AbstractHttpConfigurer::disable); // Disable CSRF if not used
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}


}
