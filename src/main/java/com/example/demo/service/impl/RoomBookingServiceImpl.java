package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.RoomBookingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository roomBookingRepository;
    private final GuestRepository guestRepository;

    public RoomBookingServiceImpl(RoomBookingRepository roomBookingRepository,
                                  GuestRepository guestRepository) {
        this.roomBookingRepository = roomBookingRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public RoomBooking createBooking(RoomBooking booking) {

        // ✅ DATE VALIDATION
        if (!booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        // 🔥 GET LOGGED-IN USER (GUEST) FROM JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email from token

        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Guest not found with email: " + email)
                );

        // ✅ SET GUEST AUTOMATICALLY
        booking.setGuest(guest);
        booking.setActive(true);

        return roomBookingRepository.save(booking);
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {

        RoomBooking existing = roomBookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found with id: " + id)
                );

        if (!booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found with id: " + id)
                );
    }

    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return roomBookingRepository.findByGuest_Id(guestId);
    }

    @Override
    public void deactivateBooking(Long id) {
        RoomBooking booking = roomBookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found with id: " + id)
                );
        booking.setActive(false);
        roomBookingRepository.save(booking);
    }
}
