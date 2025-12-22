package com.example.demo.service;

import com.example.demo.model.RoomBooking;
import java.util.List;

public interface RoomBookingService {
    RoomBooking createBooking(RoomBooking booking);
    RoomBooking getBookingById(Long id);
    List<RoomBooking> getAllBookings();
    List<RoomBooking> getBookingsByGuestId(Long guestId);  // Changed from getBookingsForGuest
    RoomBooking updateBooking(Long id, RoomBooking bookingDetails);
    void deleteBooking(Long id);
    void cancelBooking(Long id);
    void deactivateBooking(Long id);  // Added this method
    boolean isRoomAvailable(String roomNumber, LocalDateTime checkIn, LocalDateTime checkOut);
}