package com.example.demo.service;

import com.example.demo.model.DigitalKey;
import java.util.List;

public interface DigitalKeyService {
    DigitalKey generateKey(long bookingId);
    DigitalKey getKeyById(long id);
    DigitalKey getActiveKeyForBooking(long bookingId);
    List<DigitalKey> getKeysForGuest(long guestId);
}