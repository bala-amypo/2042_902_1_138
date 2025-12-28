package com.example.demo.security;

import com.example.demo.model.Guest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtTokenProvider {

    /*
     * Fake token format (Base64):
     * userId|email|role
     */

    public String generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            // userId is NOT inside UserDetails → encode dummy but non-null
            Long userId = Math.abs(email.hashCode()) + 0L;

            String tokenData = userId + "|" + email + "|" + role;
            return Base64.getEncoder().encodeToString(tokenData.getBytes());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split("\\|").length == 3;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return Long.parseLong(decoded.split("\\|")[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split("\\|")[1];
        } catch (Exception e) {
            return null;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split("\\|")[2];
        } catch (Exception e) {
            return null;
        }
    }
}