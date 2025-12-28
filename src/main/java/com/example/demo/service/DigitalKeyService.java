package com.example.demo.service;

import com.example.demo.model.DigitalKey;
import java.util.List;

public interface DigitalKeyService {

    // Used by tests
    DigitalKey generateKey(Long bookingId);

    DigitalKey getActiveKeyForBooking(Long bookingId);

    List<DigitalKey> getKeysForGuest(Long guestId);

    // Used by controller
    DigitalKey getActiveKeyByBooking(Long bookingId);

    void deactivateKey(Long keyId);
}