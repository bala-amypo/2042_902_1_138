package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final GuestRepository guestRepository;
    
    public AuthController(JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          GuestRepository guestRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.guestRepository = guestRepository;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register a new guest")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (guestRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Email already"));
            }
            
            Guest guest = new Guest();
            guest.setFullName(registerRequest.getFullName());
            guest.setEmail(registerRequest.getEmail());
            guest.setPhoneNumber(registerRequest.getPhoneNumber());
            guest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            guest.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "ROLE_USER");
            guest.setVerified(false);
            guest.setActive(true);
            guest.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            
            Guest savedGuest = guestRepository.save(guest);
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerRequest.getEmail(),
                            registerRequest.getPassword()
                    )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("guest", savedGuest);
            
            return ResponseEntity.ok(ApiResponse.success("Guest registered successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login and get JWT token")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            
            Guest guest = guestRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Guest not found"));
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("guest", guest);
            
            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid email or password"));
        }
    }
}