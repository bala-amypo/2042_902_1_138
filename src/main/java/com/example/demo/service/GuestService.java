package com.example.demo.service;

import com.example.demo.model.Guest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GuestService {

    private final Map<String, Guest> guestMap = new HashMap<>();
    private final PasswordEncoder passwordEncoder;

    public GuestService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        // Create in-memory user3@gmail.com
        Guest guest = new Guest();
        guest.setEmail("user3@gmail.com");
        guest.setPassword(passwordEncoder.encode("user3")); // encode password
        guest.setFullName("User Three");
        guest.setPhoneNumber("1234567890");
        guest.setRole("ROLE_USER");
        guest.setVerified(true);
        guest.setActive(true);

        guestMap.put(guest.getEmail(), guest);
    }

    public Guest getGuestByEmail(String email) {
        return guestMap.get(email);
    }
}
