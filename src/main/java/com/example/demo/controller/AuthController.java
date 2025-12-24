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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(GuestService guestService,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.guestService = guestService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new guest")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {

        Guest guest = new Guest();
        guest.setEmail(request.getEmail());
        guest.setPassword(passwordEncoder.encode(request.getPassword()));
        guest.setFullName(request.getFullName());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setRole("guest");
        guest.setVerified(true);
        guest.setActive(true);

        Guest createdGuest = guestService.createGuest(guest);

        return ResponseEntity.ok(
                new ApiResponse(true, "Guest registered successfully", createdGuest)
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Login guest")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {

        Guest guest = guestService.getGuestByEmail(request.getEmail());

        if (guest == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid credential"));
        }

        if (!passwordEncoder.matches(request.getPassword(), guest.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid credentials"));
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        guest.getEmail(),
                        null,
                        Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + guest.getRole().toUpperCase())
                        )
                );

        String token = jwtTokenProvider.generateToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("guest", guest);

        return ResponseEntity.ok(
                new ApiResponse(true, "Login successful", response)
        );
    }
}
