package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {
    
    private final DigitalKeyRepository digitalKeyRepository;
    private final RoomBookingRepository roomBookingRepository;
    
    public DigitalKeyServiceImpl(DigitalKeyRepository digitalKeyRepository, 
                                 RoomBookingRepository roomBookingRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.roomBookingRepository = roomBookingRepository;
    }
    
    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        if (!booking.getActive()) {
            throw new IllegalStateException("inactive");
        }
        
        DigitalKey digitalKey = new DigitalKey();
        digitalKey.setBooking(booking);
        digitalKey.setKeyValue(UUID.randomUUID().toString());
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        digitalKey.setIssuedAt(now);
        digitalKey.setExpiresAt(new Timestamp(now.getTime() + 24 * 60 * 60 * 1000));
        digitalKey.setActive(true);
        
        return digitalKeyRepository.save(digitalKey);
    }
    
    @Override
    public DigitalKey getKeyById(Long id) {
        return digitalKeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
    }
    
    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Active digital key not found"));
    }
    
    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }
}