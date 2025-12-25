package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
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
    @Operation(summary = "Register a new guest")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest) {
        try {
            Guest guest = new Guest();
            guest.setFullName(registerRequest.getFullName());
            guest.setEmail(registerRequest.getEmail());
            guest.setPhoneNumber(registerRequest.getPhoneNumber());
            guest.setPassword(registerRequest.getPassword());
            guest.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "ROLE_USER");
            guest.setVerified(false);
            guest.setActive(true);
            guest.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            
            Guest createdGuest = guestService.createGuest(guest);
            return ApiResponse.success("Guest registered successfully", createdGuest);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login and get JWT token")
    public ApiResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            
            return ApiResponse.success("Login successful", jwt);
        } catch (Exception e) {
            return ApiResponse.error("Invalid email or password");
        }
    }
}