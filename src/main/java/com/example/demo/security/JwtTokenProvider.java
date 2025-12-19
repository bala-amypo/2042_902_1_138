package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    
    private final SecretKey jwtSecret;
    
    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;
    
    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.jwtSecret = Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        Instant now = Instant.now();
        Instant expiryInstant = now.plusMillis(jwtExpirationMs);
        
        // For testing - you may need to customize this based on your Guest object
        // If your Guest implements UserDetails, you can cast it
        Long userId = 1L; // Default value for testing
        String role = "ROLE_USER"; // Default role
        
        // Try to extract from user details if possible
        try {
            // If your userDetails has getId() and getRole() methods
            // You might need to cast to your specific class
            // Guest guest = (Guest) userDetails;
            // userId = guest.getId();
            // role = guest.getRole();
        } catch (Exception e) {
            // Keep default values if casting fails
        }
        
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Usually email
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiryInstant))
                .signWith(jwtSecret)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(jwtSecret).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject(); // Email is in subject
        } catch (Exception e) {
            return null;
        }
    }
    
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            // Try to get userId from claims
            Object userIdObj = claims.get("userId");
            if (userIdObj != null) {
                if (userIdObj instanceof Long) {
                    return (Long) userIdObj;
                } else if (userIdObj instanceof Integer) {
                    return ((Integer) userIdObj).longValue();
                } else if (userIdObj instanceof String) {
                    try {
                        return Long.parseLong((String) userIdObj);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
            
            // Alternative: Extract from subject if format is "id:email"
            String subject = claims.getSubject();
            if (subject != null && subject.contains(":")) {
                String[] parts = subject.split(":");
                if (parts.length > 0) {
                    try {
                        return Long.parseLong(parts[0]);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            // Try to get role from claims
            Object roleObj = claims.get("role");
            if (roleObj != null) {
                return roleObj.toString();
            }
            
            // Try authorities claim
            Object authoritiesObj = claims.get("authorities");
            if (authoritiesObj != null) {
                return authoritiesObj.toString();
            }
            
            // Default role
            return "ROLE_USER";
        } catch (Exception e) {
            return null;
        }
    }
}