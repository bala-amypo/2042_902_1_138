package com.example.demo.controller;

import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Room Bookings")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;

    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }

    @PostMapping
    @Operation(summary = "Create new booking")
    public ResponseEntity<RoomBooking> createBooking(@RequestBody RoomBooking booking) {
        return ResponseEntity.ok(roomBookingService.createBooking(booking));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<RoomBooking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.getBookingById(id));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get bookings for guest")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(roomBookingService.getBookingsForGuest(guestId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking")
    public ResponseEntity<RoomBooking> updateBooking(
            @PathVariable Long id,
            @RequestBody RoomBooking booking) {

        return ResponseEntity.ok(roomBookingService.updateBooking(id, booking));
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate booking")
    public ResponseEntity<Void> deactivateBooking(@PathVariable Long id) {
        roomBookingService.deactivateBooking(id);
        return ResponseEntity.ok().build();
    }
}
