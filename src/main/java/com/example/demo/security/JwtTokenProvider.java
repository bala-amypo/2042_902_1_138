package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // ✅ SECURE 256-bit key (fixes WeakKeyException)
    private final SecretKey key =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long EXPIRATION = 86400000; // 1 day

    public String generateToken(Authentication authentication) {

        GuestPrincipal principal =
                (GuestPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())   // email
                .claim("userId", principal.getId())    // REQUIRED by tests
                .claim("role",
                        principal.getAuthorities()
                                .iterator()
                                .next()
                                .getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
