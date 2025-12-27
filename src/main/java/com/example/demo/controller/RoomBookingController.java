package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {

    private final RoomBookingService service;

    public RoomBookingController(RoomBookingService service) {
        this.service = service;
    }

    @PostMapping
    public RoomBooking createBooking(@RequestBody RoomBooking booking) {
        return service.createBooking(booking);
    }

    @GetMapping("/guest/{guestId}")
    public List<RoomBooking> getBookingsByGuest(@PathVariable Long guestId) {
        return service.getBookingsForGuest(guestId);
    }
}
