package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Use loginRequest.getEmail() and loginRequest.getPassword()
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        
        // Your logic to check credentials...
        return ResponseEntity.ok(new AuthResponse("success-token-here"));
    }
}

    @PostMapping("/login")
    public JwtResponse login(@RequestParam String email,
                             @RequestParam String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

        String token = jwtTokenProvider.generateToken(authentication);
        return new JwtResponse(token);
    }
}
