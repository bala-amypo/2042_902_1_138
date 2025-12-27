package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository repo;
    private final RoomBookingRepository bookingRepo;

    public DigitalKeyServiceImpl(
            DigitalKeyRepository repo,
            RoomBookingRepository bookingRepo) {
        this.repo = repo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {

    RoomBooking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Booking not found with id " + bookingId));

    if (!Boolean.TRUE.equals(booking.getActive())) {
        throw new IllegalStateException("Booking is inactive");
    }

    DigitalKey key = new DigitalKey();
    key.setBooking(booking);

    // ✅ EXPLICITLY SET VALUES (TEST-SAFE)
    key.setKeyValue(java.util.UUID.randomUUID().toString());
    key.setIssuedAt(java.time.Instant.now());
    key.setExpiresAt(key.getIssuedAt().plusSeconds(3600));
    key.setActive(true);

    return repo.save(key);
}


    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return repo.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No active key " + bookingId));
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return repo.findByBookingGuestId(guestId);
    }
}
