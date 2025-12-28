package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository digitalKeyRepository;
    private final RoomBookingRepository roomBookingRepository;

    public DigitalKeyServiceImpl(
            DigitalKeyRepository digitalKeyRepository,
            RoomBookingRepository roomBookingRepository) {

        this.digitalKeyRepository = digitalKeyRepository;
        this.roomBookingRepository = roomBookingRepository;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found: " + bookingId));

        if (!booking.getActive()) {
            throw new IllegalStateException("Booking is inactive");
        }

        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        key.setKeyValue(UUID.randomUUID().toString());
        key.setIssuedAt(Instant.now());
        key.setExpiresAt(Instant.now().plusSeconds(3600));
        key.setActive(true);

        return digitalKeyRepository.save(key);
    }

    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No active key for booking " + bookingId));
    }

    @Override
    public DigitalKey getActiveKeyByBooking(Long bookingId) {
        // Alias for controller
        return getActiveKeyForBooking(bookingId);
    }

    @Override
    public void deactivateKey(Long keyId) {
        DigitalKey key = digitalKeyRepository.findById(keyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Key not found: " + keyId));
        key.setActive(false);
        digitalKeyRepository.save(key);
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }
}