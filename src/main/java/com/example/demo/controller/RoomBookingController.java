package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;

    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @PostMapping
    public ResponseEntity<RoomBooking> createBooking(@RequestBody RoomBooking booking) {
        return ResponseEntity.ok(roomBookingService.createBooking(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.getBookingById(id));
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(roomBookingService.getBookingsForGuest(guestId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateBooking(@PathVariable Long id,
                                                     @RequestBody RoomBooking booking) {
        return ResponseEntity.ok(roomBookingService.updateBooking(id, booking));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        roomBookingService.deactivateBooking(id);
        return ResponseEntity.ok().build();
    }
}
