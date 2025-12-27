package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())
            
            // 2. Enable CORS (Crucial if using a frontend or Swagger)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 3. Configure Request Authorization
            .authorizeHttpRequests(auth -> auth
                // Allow login and registration
                .requestMatchers("/auth/**").permitAll()
                
                // CRITICAL: Allow Spring's internal error handling path
                // If login fails and redirects to /error, a 403 occurs if this isn't here
                .requestMatchers("/error").permitAll()

                // Allow Swagger UI and API Docs
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // Everything else must be authenticated
                .anyRequest().authenticated()
            )

            // 4. Set Session to Stateless (for JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }

    // 5. CORS Configuration to allow requests from your frontend/Swagger UI
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // For production, replace "*" with your domain
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}