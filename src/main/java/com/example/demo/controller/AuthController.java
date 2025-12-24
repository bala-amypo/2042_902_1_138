package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.Guest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {

        Guest guest = guestService.getGuestByEmail(request.getEmail());

        if (guest == null || !passwordEncoder.matches(request.getPassword(), guest.getPassword())) {
            return ResponseEntity.ok(new ApiResponse(false, "Invalid credentials", null));
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        guest.getEmail(),
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(guest.getRole()))
                );

        String token = jwtTokenProvider.generateToken(guest.getEmail(), guest.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("guest", guest);

        return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
    }
}
