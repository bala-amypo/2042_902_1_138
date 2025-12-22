package com.example.demo.service;

import com.example.demo.model.DigitalKey;
import java.util.List;

public interface DigitalKeyService {
    DigitalKey generateKeyForBooking(Long bookingId);
    DigitalKey getKeyById(Long id);
    DigitalKey getActiveKeyByBookingId(Long bookingId);
    List<DigitalKey> getKeysForGuest(Long guestId);  // Changed to match your interface
    List<DigitalKey> getAllKeys();
    DigitalKey updateKey(Long id, DigitalKey keyDetails);
    void deactivateKey(Long id);
    void deleteKey(Long id);
    boolean isValidKey(String keyValue);
    boolean validateKeyAccess(String keyValue, Long guestId, String roomNumber);
    void revokeKey(Long id);
    List<DigitalKey> getExpiredKeys();
    List<DigitalKey> getKeysByStatus(String status);
}