package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GuestServiceImpl implements GuestService {

    private final GuestRepository repo;
    private final PasswordEncoder encoder;

    public GuestServiceImpl(GuestRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public Guest createGuest(Guest g) {
        if (repo.existsByEmail(g.getEmail()))
            throw new IllegalArgumentException("Email already exists");
        g.setPassword(encoder.encode(g.getPassword()));
        return repo.save(g);
    }

    public Guest getGuestById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
    }

    public Guest updateGuest(Long id, Guest g) {
        Guest existing = getGuestById(id);
        existing.setFullName(g.getFullName());
        return repo.save(existing);
    }

    public void deactivateGuest(Long id) {
        Guest g = getGuestById(id);
        g.setActive(false);
    }
}
