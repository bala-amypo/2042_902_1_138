package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository keyRepo;
    private final RoomBookingRepository bookingRepo;

    public DigitalKeyServiceImpl(DigitalKeyRepository keyRepo,
                                 RoomBookingRepository bookingRepo) {
        this.keyRepo = keyRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getActive()) {
            throw new IllegalStateException("Booking inactive");
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp exp = new Timestamp(now.getTime() + 86400000);

        DigitalKey key = new DigitalKey(
                booking,
                UUID.randomUUID().toString(),
                now,
                exp,
                true
        );
        return keyRepo.save(key);
    }

    @Override
    public DigitalKey getKeyById(Long id) {
        return keyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
    }

    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return keyRepo.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return keyRepo.findByBookingGuestId(guestId);
    }
}
