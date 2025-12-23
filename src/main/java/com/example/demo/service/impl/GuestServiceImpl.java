package com.example.demo.service.impl;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final List<Guest> guests = new ArrayList<>();
    private Long idCounter = 1L;

    @Override
    public Guest createGuest(Guest guest) {
        guest.setId(idCounter++);
        guests.add(guest);
        return guest;
    }

    @Override
    public Guest getGuestByEmail(String email) {
        return guests.stream()
                .filter(g -> g.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guests.stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guests;
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existing = getGuestById(id);
        if (existing != null) {
            existing.setFullName(guest.getFullName());
            existing.setEmail(guest.getEmail());
            existing.setPhoneNumber(guest.getPhoneNumber());
            existing.setRole(guest.getRole());
            existing.setVerified(guest.isVerified());
            existing.setActive(guest.isActive());
        }
        return existing;
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest guest = getGuestById(id);
        if (guest != null) {
            guest.setActive(false);
        }
    }
}
