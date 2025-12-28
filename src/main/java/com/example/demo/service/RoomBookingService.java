package com.example.demo.service;
import com.example.demo.model.RoomBooking;
import java.util.List;

public interface RoomBookingService {
    RoomBooking createBooking(RoomBooking booking);
    List<RoomBooking> getBookingsForGuest(Long guestId);
    RoomBooking updateBooking(Long id, RoomBooking details);
}