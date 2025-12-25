package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(GuestService guestService, JwtTokenProvider jwtTokenProvider) {
        this.guestService = guestService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerGuest(@RequestBody RegisterRequest registerRequest) {
        // Convert DTO to entity
        Guest guest = new Guest();
        guest.setName(registerRequest.getName());
        guest.setEmail(registerRequest.getEmail());
        guest.setPassword(registerRequest.getPassword());

        guestService.addGuest(guest);

        return ResponseEntity.ok("Guest registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RegisterRequest request) {
        Guest guest = guestService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (guest != null) {
            String token = jwtTokenProvider.generateToken(guest.getEmail(), "USER");
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
