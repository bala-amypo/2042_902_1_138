package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DigitalKeyService;

import java.time.Instant;
import java.util.*;

public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository keyRepo;
    private final RoomBookingRepository bookingRepo;

    public DigitalKeyServiceImpl(DigitalKeyRepository k, RoomBookingRepository b) {
        this.keyRepo = k;
        this.bookingRepo = b;
    }

    public DigitalKey generateKey(Long bookingId) {
        RoomBooking b = bookingRepo.findById(bookingId)
                .orElseThrow(ResourceNotFoundException::new);
        if (!b.isActive()) throw new IllegalStateException();

        DigitalKey k = new DigitalKey();
        k.setBooking(b);
        k.setKeyValue(UUID.randomUUID().toString());
        k.setIssuedAt(Instant.now());
        k.setExpiresAt(Instant.now().plusSeconds(3600));
        return keyRepo.save(k);
    }

    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return keyRepo.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return keyRepo.findByBookingGuestId(guestId);
    }
}
