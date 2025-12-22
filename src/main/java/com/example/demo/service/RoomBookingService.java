package com.example.demo.service;

import com.example.demo.model.RoomBooking;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomBookingService {
    
    RoomBooking createBooking(RoomBooking booking);
    RoomBooking getBookingById(Long id);
    List<RoomBooking> getAllBookings();
    List<RoomBooking> getBookingsByGuestId(Long guestId);
    RoomBooking updateBooking(Long id, RoomBooking bookingDetails);
    void deleteBooking(Long id);
    void cancelBooking(Long id);
    boolean isRoomAvailable(String roomNumber, LocalDateTime checkIn, LocalDateTime checkOut);
}