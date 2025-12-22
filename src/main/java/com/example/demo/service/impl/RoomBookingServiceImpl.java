package com.example.demo.service.impl;

import com.example.demo.model.RoomBooking;
import com.example.demo.model.Guest;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomBookingServiceImpl implements RoomBookingService {

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public RoomBooking createBooking(RoomBooking booking) {
        // Validate check-in is before check-out
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        // Ensure guest exists
        Optional<Guest> guest = guestRepository.findById(booking.getGuest().getId());
        if (!guest.isPresent()) {
            throw new IllegalArgumentException("Guest not found");
        }
        
        // Set guest to booking
        booking.setGuest(guest.get());
        
        // Set booking timestamp
        booking.setBookingDate(LocalDateTime.now());
        
        // Set default status
        booking.setStatus("ACTIVE");
        
        // Save booking
        return roomBookingRepository.save(booking);
    }

    @Override
    public RoomBooking getBookingById(Long id) {
        Optional<RoomBooking> booking = roomBookingRepository.findById(id);
        return booking.orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    @Override
    public List<RoomBooking> getAllBookings() {
        return roomBookingRepository.findAll();
    }

    @Override
    public List<RoomBooking> getBookingsByGuestId(Long guestId) {
        // Check if guest exists
        boolean guestExists = guestRepository.existsById(guestId);
        if (!guestExists) {
            throw new IllegalArgumentException("Guest not found with id: " + guestId);
        }
        
        return roomBookingRepository.findByGuestId(guestId);
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking bookingDetails) {
        RoomBooking booking = getBookingById(id);
        
        // Update fields if provided
        if (bookingDetails.getCheckInDate() != null) {
            booking.setCheckInDate(bookingDetails.getCheckInDate());
        }
        
        if (bookingDetails.getCheckOutDate() != null) {
            booking.setCheckOutDate(bookingDetails.getCheckOutDate());
        }
        
        // Validate dates if both are being updated
        if (bookingDetails.getCheckInDate() != null && bookingDetails.getCheckOutDate() != null) {
            if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
                throw new IllegalArgumentException("Check-in date must be before check-out date");
            }
        }
        
        return roomBookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        RoomBooking booking = getBookingById(id);
        roomBookingRepository.delete(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        RoomBooking booking = getBookingById(id);
        booking.setStatus("CANCELLED");
        roomBookingRepository.save(booking);
    }

    @Override
    public void deactivateBooking(Long id) {
        RoomBooking booking = getBookingById(id);
        booking.setStatus("INACTIVE");
        roomBookingRepository.save(booking);
    }

    @Override
    public boolean isRoomAvailable(String roomNumber, LocalDateTime checkIn, LocalDateTime checkOut) {
        // Validate dates
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        // Check for overlapping bookings
        List<RoomBooking> existingBookings = roomBookingRepository.findByRoomNumber(roomNumber);
        
        for (RoomBooking booking : existingBookings) {
            // Skip cancelled/inactive bookings
            if ("CANCELLED".equals(booking.getStatus()) || "INACTIVE".equals(booking.getStatus())) {
                continue;
            }
            
            // Check if the requested dates overlap with existing booking
            boolean checkInDuringBooking = 
                !checkIn.isBefore(booking.getCheckInDate()) && 
                checkIn.isBefore(booking.getCheckOutDate());
                
            boolean checkOutDuringBooking = 
                checkOut.isAfter(booking.getCheckInDate()) && 
                !checkOut.isAfter(booking.getCheckOutDate());
                
            boolean bookingDuringRequestedDates = 
                booking.getCheckInDate().isBefore(checkOut) && 
                booking.getCheckOutDate().isAfter(checkIn);
            
            if (checkInDuringBooking || checkOutDuringBooking || bookingDuringRequestedDates) {
                return false; // Room not available
            }
        }
        
        return true; // Room available
    }
}