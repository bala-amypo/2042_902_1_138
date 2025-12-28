package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.TokenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        TokenResponse response = new TokenResponse();
        response.setSuccess(true);
        response.setMessage("Login successful");
        response.setToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.demo-token");
        response.setUser(Map.of(
            "email", loginRequest.getEmail(),
            "role", "USER"
        ));
        return response;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequestDTO registerRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("data", Map.of(
            "user", Map.of(
                "email", registerRequest.getEmail(),
                "fullName", registerRequest.getFullName(),
                "role", "USER"
            )
        ));
        return response;
    }
}