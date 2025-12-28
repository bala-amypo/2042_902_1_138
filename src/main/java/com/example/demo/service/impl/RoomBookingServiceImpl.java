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
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        return roomBookingRepository.save(booking);
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {
        RoomBooking existing = roomBookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found: " + id));

        existing.setCheckInDate(booking.getCheckInDate());
        existing.setCheckOutDate(booking.getCheckOutDate());
        return roomBookingRepository.save(existing);
    }

    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return roomBookingRepository.findByGuestId(guestId);
    }

    @Override
    public RoomBooking getBookingById(Long id) {
        return roomBookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found: " + id));
    }

    @Override
    public List<RoomBooking> getBookingsByGuest(Long guestId) {
        // Alias for controller
        return getBookingsForGuest(guestId);
    }
}