package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository repo;
    private final PasswordEncoder encoder;

    public GuestServiceImpl(GuestRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public Guest createGuest(Guest guest) {
        if (repo.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        guest.setPassword(encoder.encode(guest.getPassword()));
        return repo.save(guest);
    }

    @Override
    public Guest getGuestById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found with id " + id));
    }

    @Override
    public Guest updateGuest(Long id, Guest update) {
        Guest existing = getGuestById(id);

        extracted(update, existing);

        return repo.save(existing);
    }

    private static void extracted(Guest update, Guest existing) {
        if (update.getFullName() != null) existing.setFullName(update.getFullName());
        if (update.getPhoneNumber() != null) existing.setPhoneNumber(update.getPhoneNumber());
        if (update.getRole() != null) existing.setRole(update.getRole());
        if (update.getVerified() != null) existing.setVerified(update.getVerified());
        if (update.getActive() != null) existing.setActive(update.getActive());
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest g = getGuestById(id);
        g.setActive(false);
        repo.save(g);
    }

    @Override
    public List<Guest> getAllGuests() {
        return repo.findAll();
    }
}
