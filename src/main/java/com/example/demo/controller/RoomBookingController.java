package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {

    @Autowired
    private RoomBookingService bookingService;

    // Create booking
    @PostMapping
    public ResponseEntity<RoomBooking> createBooking(@RequestBody RoomBooking booking) {
        RoomBooking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(createdBooking);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<RoomBooking>> getAllBookings() {
        List<RoomBooking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBookingById(@PathVariable Long id) {
        RoomBooking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    // Get bookings by guest ID - FIXED METHOD NAME
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<RoomBooking>> getBookingsByGuestId(@PathVariable Long guestId) {
        List<RoomBooking> bookings = bookingService.getBookingsByGuestId(guestId);
        return ResponseEntity.ok(bookings);
    }

    // Update booking
    @PutMapping("/{id}")
    public ResponseEntity<RoomBooking> updateBooking(@PathVariable Long id, @RequestBody RoomBooking bookingDetails) {
        RoomBooking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        return ResponseEntity.ok(updatedBooking);
    }

    // Delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // Cancel booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    // Deactivate booking - FIXED METHOD NAME
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateBooking(@PathVariable Long id) {
        bookingService.deactivateBooking(id);
        return ResponseEntity.ok().build();
    }

    // Check room availability
    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam String roomNumber,
            @RequestParam String checkIn,
            @RequestParam String checkOut) {
        // This is a simplified version - you'll need to parse dates
        boolean isAvailable = bookingService.isRoomAvailable(roomNumber, 
            java.time.LocalDateTime.parse(checkIn), 
            java.time.LocalDateTime.parse(checkOut));
        return ResponseEntity.ok(isAvailable);
    }
}