package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // CHANGE: Use @RequestBody instead of @RequestParam
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Now you get data from the body
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        
        // Your authentication logic here...
        return ResponseEntity.ok("Login Successful");
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
