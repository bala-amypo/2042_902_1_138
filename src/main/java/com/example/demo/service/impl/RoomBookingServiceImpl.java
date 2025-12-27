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
        if (booking.getGuest() == null || booking.getGuest().getId() == null) {
            throw new IllegalArgumentException("Guest is required for booking");
        }
        if (booking.getCheckInDate() != null && booking.getCheckOutDate() != null && 
            !booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        return roomBookingRepository.save(booking);
    }

    @Override
    public List<RoomBooking> getAllBookings() {
        return roomBookingRepository.findAll();
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {
        RoomBooking existing = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        
        if (booking.getCheckInDate() != null && booking.getCheckOutDate() != null && 
            !booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        if (booking.getRoomNumber() != null) existing.setRoomNumber(booking.getRoomNumber());
        if (booking.getCheckInDate() != null) existing.setCheckInDate(booking.getCheckInDate());
        if (booking.getCheckOutDate() != null) existing.setCheckOutDate(booking.getCheckOutDate());
        if (booking.getActive() != null) existing.setActive(booking.getActive());
        
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
        RoomBooking booking = getBookingById(id);
        booking.setActive(false);
        roomBookingRepository.save(booking);
    }
}