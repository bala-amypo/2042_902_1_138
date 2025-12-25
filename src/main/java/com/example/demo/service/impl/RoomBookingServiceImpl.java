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
        RoomBooking existingBooking = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        if (booking.getCheckInDate() != null && booking.getCheckOutDate() != null && 
            !booking.getCheckInDate().isBefore(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        existingBooking.setRoomNumber(booking.getRoomNumber());
        existingBooking.setCheckInDate(booking.getCheckInDate());
        existingBooking.setCheckOutDate(booking.getCheckOutDate());
        existingBooking.setActive(booking.getActive());
        
        return roomBookingRepository.save(existingBooking);
    }
    
    @Override
    public RoomBooking getBookingById(Long id) {
        return roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }
    
    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return roomBookingRepository.findByGuestId(guestId);
    }
    
    @Override
    public void deactivateBooking(Long id) {
        RoomBooking booking = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setActive(false);
        roomBookingRepository.save(booking);
    }
}