package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String generateToken(String email, String role) {
        // Dummy token for testing, in real app use JWT library
        return "dummy-jwt-token-for-" + email;
    }
}
