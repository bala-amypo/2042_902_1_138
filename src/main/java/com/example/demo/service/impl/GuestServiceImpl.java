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
    
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    
    public GuestServiceImpl(GuestRepository guestRepository, PasswordEncoder passwordEncoder) {
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Guest createGuest(Guest guest) {
        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already");
        }
        
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        return guestRepository.save(guest);
    }
    
    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existingGuest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
        
        existingGuest.setFullName(guest.getFullName());
        existingGuest.setPhoneNumber(guest.getPhoneNumber());
        existingGuest.setVerified(guest.getVerified());
        existingGuest.setActive(guest.getActive());
        existingGuest.setRole(guest.getRole());
        
        return guestRepository.save(existingGuest);
    }
    
    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
    }
    
    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }
    
    @Override
    public void deactivateGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
        
        guest.setActive(false);
        guestRepository.save(guest);
    }
}