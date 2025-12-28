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
@Tag(name = "Bookings", description = "Room Booking Operations")
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

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get bookings for guest")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(roomBookingService.getBookingsForGuest(guestId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking")
    public ResponseEntity<RoomBooking> updateBooking(@PathVariable Long id, @RequestBody RoomBooking booking) {
        return ResponseEntity.ok(roomBookingService.updateBooking(id, booking));
    }
}