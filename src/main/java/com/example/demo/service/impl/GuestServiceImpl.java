package com.example.demo.service.impl;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest updateGuest(Long id, Guest updatedGuest) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (!optionalGuest.isPresent()) {
            return null;
        }
        Guest existingGuest = optionalGuest.get();
        existingGuest.setFullName(updatedGuest.getFullName());
        existingGuest.setEmail(updatedGuest.getEmail());
        existingGuest.setPhoneNumber(updatedGuest.getPhoneNumber());
        existingGuest.setPassword(updatedGuest.getPassword());
        existingGuest.setActive(updatedGuest.isActive());
        existingGuest.setVerified(updatedGuest.isVerified());
        existingGuest.setRole(updatedGuest.getRole());
        return guestRepository.save(existingGuest);
    }

    @Override
    public boolean deactivateGuest(Long id) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (!optionalGuest.isPresent()) {
            return false;
        }
        Guest guest = optionalGuest.get();
        guest.setActive(false);
        guestRepository.save(guest);
        return true;
    }

    @Override
    public Guest getGuestByEmail(String email) {
        return guestRepository.findByEmail(email).orElse(null);
    }
}
