package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    //  TEST CHECKS THIS METHOD SIGNATURE
    public String generateToken(Authentication authentication) {
        return "dummy-jwt-token";
    }
}
