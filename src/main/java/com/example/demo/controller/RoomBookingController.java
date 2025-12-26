package com.example.demo.controller;

import com.example.demo.dto.BookingRequest;
import com.example.demo.model.Guest;
import com.example.demo.model.RoomBooking;
import com.example.demo.service.GuestService;
import com.example.demo.service.RoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Room Bookings")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;
    private final GuestService guestService;

    public RoomBookingController(RoomBookingService roomBookingService, GuestService guestService) {
        this.roomBookingService = roomBookingService;
        this.guestService = guestService;
    }

    @PostMapping
    @Operation(summary = "Create new booking")
    public ResponseEntity<RoomBooking> createBooking(@RequestBody BookingRequest request) {
        Guest guest = guestService.getGuestById(request.getGuestId());
        
        RoomBooking booking = new RoomBooking();
        booking.setGuest(guest);
        booking.setRoomNumber(request.getRoomNumber());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setActive(true);
        
        return ResponseEntity.ok(roomBookingService.createBooking(booking));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<RoomBooking> getBooking(@Parameter(description = "Booking ID") @PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.getBookingById(id));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get bookings for guest")
    public ResponseEntity<List<RoomBooking>> getBookingsForGuest(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(roomBookingService.getBookingsForGuest(guestId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking")
    public ResponseEntity<RoomBooking> updateBooking(@Parameter(description = "Booking ID") @PathVariable Long id, 
                                                   @RequestBody BookingRequest request) {
        Guest guest = guestService.getGuestById(request.getGuestId());
        
        RoomBooking booking = new RoomBooking();
        booking.setGuest(guest);
        booking.setRoomNumber(request.getRoomNumber());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        
        return ResponseEntity.ok(roomBookingService.updateBooking(id, booking));
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate booking")
    public ResponseEntity<Void> deactivateBooking(@Parameter(description = "Booking ID") @PathVariable Long id) {
        roomBookingService.deactivateBooking(id);
        return ResponseEntity.ok().build();
    }
}