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
    
    public DigitalKeyServiceImpl(DigitalKeyRepository digitalKeyRepository, RoomBookingRepository roomBookingRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.roomBookingRepository = roomBookingRepository;
    }
    
    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        if (!booking.getActive()) {
            throw new IllegalStateException("Cannot generate key for inactive booking");
        }
        
        String keyValue = UUID.randomUUID().toString();
        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());
        Timestamp expiresAt = new Timestamp(issuedAt.getTime() + 24 * 60 * 60 * 1000); // 24 hours
        
        DigitalKey digitalKey = new DigitalKey(booking, keyValue, issuedAt, expiresAt, true);
        return digitalKeyRepository.save(digitalKey);
    }
    
    @Override
    public DigitalKey getKeyById(Long id) {
        return digitalKeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
    }
    
    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Active key not found for booking"));
    }
    
    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }
}