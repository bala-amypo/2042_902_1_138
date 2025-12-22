package com.example.demo.service.impl;

import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DigitalKeyServiceImpl implements DigitalKeyService {

    @Autowired
    private DigitalKeyRepository digitalKeyRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Override
    public DigitalKey generateKeyForBooking(Long bookingId) {
        // Find the booking
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Check if booking is active - FIXED LINE
        if (!"ACTIVE".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot generate key for inactive booking");
        }

        // Check if booking already has an active key
        Optional<DigitalKey> existingKey = digitalKeyRepository.findByBookingIdAndStatus(bookingId, "ACTIVE");
        if (existingKey.isPresent()) {
            throw new RuntimeException("Active key already exists for this booking");
        }

        // Generate unique key value
        String keyValue = UUID.randomUUID().toString();

        // Create digital key
        DigitalKey digitalKey = new DigitalKey();
        digitalKey.setKeyValue(keyValue);
        digitalKey.setBooking(booking);
        digitalKey.setIssueTime(LocalDateTime.now());
        digitalKey.setExpiryTime(booking.getCheckOutDate()); // Expires at checkout
        digitalKey.setStatus("ACTIVE");

        // Save and return
        return digitalKeyRepository.save(digitalKey);
    }

    @Override
    public DigitalKey getKeyById(Long id) {
        return digitalKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Digital key not found with id: " + id));
    }

    @Override
    public DigitalKey getActiveKeyByBookingId(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndStatus(bookingId, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("No active key found for booking id: " + bookingId));
    }

    @Override
    public List<DigitalKey> getKeysByGuestId(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }

    @Override
    public List<DigitalKey> getAllKeys() {
        return digitalKeyRepository.findAll();
    }

    @Override
    public DigitalKey updateKey(Long id, DigitalKey keyDetails) {
        DigitalKey key = getKeyById(id);

        // Update fields if provided
        if (keyDetails.getExpiryTime() != null) {
            key.setExpiryTime(keyDetails.getExpiryTime());
        }

        if (keyDetails.getStatus() != null) {
            key.setStatus(keyDetails.getStatus());
        }

        return digitalKeyRepository.save(key);
    }

    @Override
    public void deactivateKey(Long id) {
        DigitalKey key = getKeyById(id);
        key.setStatus("INACTIVE");
        digitalKeyRepository.save(key);
    }

    @Override
    public void deleteKey(Long id) {
        DigitalKey key = getKeyById(id);
        digitalKeyRepository.delete(key);
    }

    @Override
    public boolean isValidKey(String keyValue) {
        Optional<DigitalKey> key = digitalKeyRepository.findByKeyValue(keyValue);
        
        if (!key.isPresent()) {
            return false; // Key doesn't exist
        }

        DigitalKey digitalKey = key.get();
        
        // Check if key is active
        if (!"ACTIVE".equals(digitalKey.getStatus())) {
            return false;
        }

        // Check if key is expired
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(digitalKey.getExpiryTime())) {
            return false;
        }

        // Check if key is issued in the future (shouldn't happen, but just in case)
        if (now.isBefore(digitalKey.getIssueTime())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean validateKeyAccess(String keyValue, Long guestId, String roomNumber) {
        Optional<DigitalKey> key = digitalKeyRepository.findByKeyValue(keyValue);
        
        if (!key.isPresent()) {
            return false;
        }

        DigitalKey digitalKey = key.get();
        
        // Check basic key validity
        if (!isValidKey(keyValue)) {
            return false;
        }

        // Check if key belongs to the specified room
        if (!digitalKey.getBooking().getRoomNumber().equals(roomNumber)) {
            return false;
        }

        // Check if guest has access to this key
        // Primary guest always has access
        if (digitalKey.getBooking().getGuest().getId().equals(guestId)) {
            return true;
        }

        // TODO: Add sharing logic here if you have key sharing feature
        // For now, only primary guest has access
        
        return false;
    }

    @Override
    public void revokeKey(Long id) {
        DigitalKey key = getKeyById(id);
        key.setStatus("REVOKED");
        key.setExpiryTime(LocalDateTime.now()); // Expire immediately
        digitalKeyRepository.save(key);
    }

    @Override
    public List<DigitalKey> getExpiredKeys() {
        LocalDateTime now = LocalDateTime.now();
        return digitalKeyRepository.findByExpiryTimeBeforeAndStatus(now, "ACTIVE");
    }

    @Override
    public List<DigitalKey> getKeysByStatus(String status) {
        return digitalKeyRepository.findByStatus(status);
    }
}