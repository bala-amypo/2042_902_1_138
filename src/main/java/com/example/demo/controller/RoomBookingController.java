package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {

    private final RoomBookingService bookingService;

    public RoomBookingController(RoomBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public RoomBooking createBooking(@RequestBody RoomBooking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{id}")
    public RoomBooking updateBooking(@PathVariable Long id,
                                     @RequestBody RoomBooking booking) {
        return bookingService.updateBooking(id, booking);
    }

    @GetMapping("/{id}")
    public RoomBooking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/guest/{guestId}")
    public List<RoomBooking> getBookingsByGuest(@PathVariable Long guestId) {
        return bookingService.getBookingsByGuest(guestId);
    }
}