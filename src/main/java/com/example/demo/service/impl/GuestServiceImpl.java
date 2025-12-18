package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuestServiceImpl implements GuestService {
    
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    
    public GuestServiceImpl(GuestRepository guestRepository, PasswordEncoder passwordEncoder) {
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Guest createGuest(Guest guest) {
        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        return guestRepository.save(guest);
    }
    
    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existingGuest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        
        if (guest.getFullName() != null) existingGuest.setFullName(guest.getFullName());
        if (guest.getPhoneNumber() != null) existingGuest.setPhoneNumber(guest.getPhoneNumber());
        if (guest.getVerified() != null) existingGuest.setVerified(guest.getVerified());
        if (guest.getActive() != null) existingGuest.setActive(guest.getActive());
        if (guest.getRole() != null) existingGuest.setRole(guest.getRole());
        
        return guestRepository.save(existingGuest);
    }
    
    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
    }
    
    @Override
    public Guest getGuestByEmail(String email) {
        return guestRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
    }
    
    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }
    
    @Override
    public void deactivateGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        guest.setActive(false);
        guestRepository.save(guest);
    }
}