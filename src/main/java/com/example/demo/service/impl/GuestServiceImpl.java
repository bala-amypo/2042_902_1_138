package com.example.demo.service.impl;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    // ✅ Constructor Injection (MANDATORY for test cases)
    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public Guest createGuest(Guest guest) {
        if (guestRepository.findByEmail(guest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existing = getGuestById(id);

        existing.setFullName(guest.getFullName());
        existing.setPhoneNumber(guest.getPhoneNumber());
        existing.setVerified(guest.getVerified());
        existing.setActive(guest.getActive());
        existing.setRole(guest.getRole());

        return guestRepository.save(existing);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
    }

    // 🔥 THIS WAS MISSING – NOW FIXED
    @Override
    public Guest getGuestByEmail(String email) {
        return guestRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest g = getGuestById(id);
        g.setActive(false);
        guestRepository.save(g);
    }
}
