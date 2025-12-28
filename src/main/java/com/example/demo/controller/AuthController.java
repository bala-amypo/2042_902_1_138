package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Login and Registration")
public class AuthController {

    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(GuestService guestService, JwtTokenProvider jwtTokenProvider, 
                         PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.guestService = guestService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new guest")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        Guest guest = new Guest();
        guest.setEmail(request.getEmail());
        guest.setPassword(request.getPassword()); // Service will encode this
        guest.setFullName(request.getFullName());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");
        
        Guest created = guestService.createGuest(guest);
        return ResponseEntity.ok(new ApiResponse(true, "Guest registered successfully", created));
    }

    @PostMapping("/login")
    @Operation(summary = "Login guest")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        String token = jwtTokenProvider.generateToken(authentication);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", request.getEmail());
        
        return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
    }
}