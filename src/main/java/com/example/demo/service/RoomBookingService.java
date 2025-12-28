package com.example.demo.service;

import com.example.demo.model.RoomBooking;
import java.util.List;

public interface RoomBookingService {

    // Used by tests
    RoomBooking createBooking(RoomBooking booking);

    RoomBooking updateBooking(Long id, RoomBooking booking);

    List<RoomBooking> getBookingsForGuest(Long guestId);

    // Used by controller
    RoomBooking getBookingById(Long id);

    List<RoomBooking> getBookingsByGuest(Long guestId);
}