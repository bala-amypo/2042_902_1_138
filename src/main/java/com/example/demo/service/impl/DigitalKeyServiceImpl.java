package com.example.demo.service.impl;

import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {
    private final DigitalKeyRepository keyRepository;
    private final RoomBookingRepository bookingRepository;
    
    public DigitalKeyServiceImpl(DigitalKeyRepository keyRepository, 
                                RoomBookingRepository bookingRepository) {
        this.keyRepository = keyRepository;
        this.bookingRepository = bookingRepository;
    }
    
    @Override
    public DigitalKey generateKey(long bookingId) {
        RoomBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        if (!booking.getActive()) {
            throw new IllegalStateException("Booking is inactive");
        }
        
        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        key.setKeyValue(UUID.randomUUID().toString());
        key.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()));
        key.setExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        key.setActive(true);
        
        return keyRepository.save(key);
    }
    
    @Override
    public DigitalKey getKeyById(long id) {
        return keyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
    }
    
    @Override
    public DigitalKey getActiveKeyForBooking(long bookingId) {
        return keyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("No active key found"));
    }
    
    @Override
    public List<DigitalKey> getKeysForGuest(long guestId) {
        return keyRepository.findByBookingGuestId(guestId);
    }
}