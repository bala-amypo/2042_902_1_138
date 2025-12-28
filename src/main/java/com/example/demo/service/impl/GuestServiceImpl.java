package com.example.demo.service.impl;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class GuestServiceImpl {

    private final GuestRepository repo;
    private final PasswordEncoder encoder;

    public GuestServiceImpl(GuestRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public Guest createGuest(Guest g) {
        if (repo.existsByEmail(g.getEmail()))
            throw new IllegalArgumentException("Email already");
        g.setPassword(encoder.encode(g.getPassword()));
        return repo.save(g);
    }

    public Guest getGuestById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("" + id));
    }

    public List<Guest> getAllGuests() {
        return repo.findAll();
    }
}
