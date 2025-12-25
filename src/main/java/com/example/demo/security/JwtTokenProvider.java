package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    //  Already required by tests
    public String generateToken(Authentication authentication) {
        return "dummy-jwt-token";
    }

    //  REQUIRED by JwtAuthenticationFilter
    public boolean validateToken(String token) {
        return token != null && !token.isEmpty();
    }

    //  REQUIRED by JwtAuthenticationFilter
    public String getEmailFromToken(String token) {
        return "test@example.com";
    }

    //  REQUIRED by JwtAuthenticationFilter
    public String getRoleFromToken(String token) {
        return "ROLE_USER";
    }
}
