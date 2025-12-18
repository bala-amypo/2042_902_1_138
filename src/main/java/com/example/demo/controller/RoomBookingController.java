package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {
    private final RoomBookingService bookingService;
    
    public RoomBookingController(RoomBookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping("/")
    public ResponseEntity<RoomBooking> createBooking(@RequestBody RoomBooking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateBooking(@PathVariable Long id, @RequestBody RoomBooking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(bookingService.getBookingsForGuest(guestId));
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateBooking(@PathVariable Long id) {
        bookingService.deactivateBooking(id);
        return ResponseEntity.ok().build();
    }
}