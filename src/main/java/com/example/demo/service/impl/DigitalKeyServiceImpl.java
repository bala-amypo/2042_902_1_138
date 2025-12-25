package com.example.demo.service.impl;

import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository digitalKeyRepository;
    private final RoomBookingRepository roomBookingRepository;

    //  Constructor Injection (TEST SUITE REQUIRES THIS)
    public DigitalKeyServiceImpl(
            DigitalKeyRepository digitalKeyRepository,
            RoomBookingRepository roomBookingRepository
    ) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.roomBookingRepository = roomBookingRepository;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!Boolean.TRUE.equals(booking.getActive())) {
            throw new IllegalStateException("inactive booking");
        }

        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        key.setKeyValue("KEY-" + bookingId + "-" + System.currentTimeMillis());
        key.setIssuedAt(Timestamp.from(Instant.now()));
        key.setExpiresAt(Timestamp.from(Instant.now().plusSeconds(86400))); // 1 day
        key.setActive(true);

        return digitalKeyRepository.save(key);
    }

    @Override
    public DigitalKey getKeyById(Long id) {
        return digitalKeyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
    }

    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Active key not found"));
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }
}
