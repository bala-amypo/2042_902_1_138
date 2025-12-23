package com.example.demo.service.impl;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Guest existing = guestRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setFullName(guest.getFullName());
        existing.setPhoneNumber(guest.getPhoneNumber());
        existing.setEmail(guest.getEmail());

        return guestRepository.save(existing);
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest guest = guestRepository.findById(id).orElse(null);

        if (guest != null) {
            guest.setActive(false);
            guestRepository.save(guest);
        }
    }
}
