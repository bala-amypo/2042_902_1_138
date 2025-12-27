
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

    public DigitalKeyServiceImpl(DigitalKeyRepository digitalKeyRepository, RoomBookingRepository roomBookingRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.roomBookingRepository = roomBookingRepository;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        
        if (!booking.getActive()) {
            throw new IllegalStateException("Cannot generate key for inactive booking");
        }
        
        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        key.setKeyValue(UUID.randomUUID().toString());
        key.setIssuedAt(Instant.now());
        key.setExpiresAt(Instant.now().plusSeconds(86400)); // 24 hours
        key.setActive(true);
        
        return digitalKeyRepository.save(key);
    }

    @Override
    public DigitalKey getKeyById(Long id) {
        return digitalKeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Key not found with id: " + id));
    }

    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Active key not found for booking: " + bookingId));
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }
}


GeustServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;

    public GuestServiceImpl(GuestRepository guestRepository,
                            PasswordEncoder passwordEncoder) {
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Guest createGuest(Guest guest) {

        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // ✅ PASSWORD ENCODE
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));

        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {

        Guest existing = guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + id
                        )
                );

        existing.setFullName(guest.getFullName());
        existing.setPhoneNumber(guest.getPhoneNumber());
        existing.setVerified(guest.getVerified());
        existing.setActive(guest.getActive());
        existing.setRole(guest.getRole());

        return guestRepository.save(existing);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + id
                        )
                );
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Guest not found with id: " + id
                        )
                );
        guest.setActive(false);
        guestRepository.save(guest);
    }
}



KeyShareRequestServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository keyShareRequestRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository keyShareRequestRepository, 
                                    DigitalKeyRepository digitalKeyRepository, 
                                    GuestRepository guestRepository) {
        this.keyShareRequestRepository = keyShareRequestRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {
        if (request.getShareEnd() != null && request.getShareStart() != null && 
            !request.getShareEnd().isAfter(request.getShareStart())) {
            throw new IllegalArgumentException("Share end time must be after share start time");
        }
        
        if (request.getSharedBy() != null && request.getSharedWith() != null && 
            request.getSharedBy().getId().equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be the same guest");
        }
        
        // Verify entities exist
        digitalKeyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        guestRepository.findById(request.getSharedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("SharedBy guest not found"));
        guestRepository.findById(request.getSharedWith().getId())
                .orElseThrow(() -> new ResourceNotFoundException("SharedWith guest not found"));
        
        request.setStatus("PENDING");
        return keyShareRequestRepository.save(request);
    }

    @Override
    public KeyShareRequest updateStatus(Long requestId, String status) {
        KeyShareRequest request = keyShareRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Share request not found with id: " + requestId));
        request.setStatus(status);
        return keyShareRequestRepository.save(request);
    }

    @Override
    public KeyShareRequest getShareRequestById(Long id) {
        return keyShareRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Share request not found with id: " + id));
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return keyShareRequestRepository.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return keyShareRequestRepository.findBySharedWithId(guestId);
    }
}


RoomBookingServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.RoomBookingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository roomBookingRepository;

    public RoomBookingServiceImpl(RoomBookingRepository roomBookingRepository) {
        this.roomBookingRepository = roomBookingRepository;
    }

    @Override
    public RoomBooking createBooking(RoomBooking booking) {
        if (booking.getCheckInDate() != null && booking.getCheckOutDate() != null && 
            !booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        return roomBookingRepository.save(booking);
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {
        RoomBooking existing = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        if (booking.getCheckInDate() != null && booking.getCheckOutDate() != null && 
            !booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        existing.setRoomNumber(booking.getRoomNumber());
        existing.setCheckInDate(booking.getCheckInDate());
        existing.setCheckOutDate(booking.getCheckOutDate());
        existing.setActive(booking.getActive());
        
        return roomBookingRepository.save(existing);
    }

    @Override
    public RoomBooking getBookingById(Long id) {
        return roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return roomBookingRepository.findByGuestId(guestId);
    }

    @Override
    public void deactivateBooking(Long id) {
        RoomBooking booking = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        booking.setActive(false);
        roomBookingRepository.save(booking);
    }
}



AccessLogService.java
package com.example.demo.service;

import com.example.demo.model.AccessLog;
import java.util.List;

public interface AccessLogService {
    AccessLog createLog(AccessLog log);
    List<AccessLog> getLogsForKey(Long keyId);
    List<AccessLog> getLogsForGuest(Long guestId);
}



DigitalKeyService.java
package com.example.demo.service;

import com.example.demo.model.DigitalKey;
import java.util.List;

public interface DigitalKeyService {
    DigitalKey generateKey(Long bookingId);
    DigitalKey getKeyById(Long id);
    DigitalKey getActiveKeyForBooking(Long bookingId);
    List<DigitalKey> getKeysForGuest(Long guestId);
}


GeustService.java
package com.example.demo.service;

import com.example.demo.model.Guest;
import java.util.List;

public interface GuestService {
    Guest createGuest(Guest guest);
    Guest updateGuest(Long id, Guest guest);
    Guest getGuestById(Long id);
    List<Guest> getAllGuests();
    void deactivateGuest(Long id);
}


KeyShareRequestService.java
package com.example.demo.service;

import com.example.demo.model.KeyShareRequest;
import java.util.List;

public interface KeyShareRequestService {
    KeyShareRequest createShareRequest(KeyShareRequest request);
    KeyShareRequest updateStatus(Long requestId, String status);
    KeyShareRequest getShareRequestById(Long id);
    List<KeyShareRequest> getRequestsSharedBy(Long guestId);
    List<KeyShareRequest> getRequestsSharedWith(Long guestId);
}


RoomBookingService.java
package com.example.demo.service;

import com.example.demo.model.RoomBooking;
import java.util.List;

public interface RoomBookingService {
    RoomBooking createBooking(RoomBooking booking);
    RoomBooking updateBooking(Long id, RoomBooking booking);
    RoomBooking getBookingById(Long id);
    List<RoomBooking> getBookingsForGuest(Long guestId);
    void deactivateBooking(Long id);
}