package com.example.demo.service.impl;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public Guest createGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    public Guest getGuestByEmail(String email) {
        return guestRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Optional<Guest> existing = guestRepository.findById(id);
        if (existing.isPresent()) {
            Guest g = existing.get();
            g.setFullName(guest.getFullName());
            g.setPhoneNumber(guest.getPhoneNumber());
            g.setActive(guest.isActive());
            return guestRepository.save(g);
        }
        return null;
    }

    @Override
    public void deactivateGuest(Long id) {
        guestRepository.findById(id).ifPresent(g -> {
            g.setActive(false);
            guestRepository.save(g);
        });
    }
}
