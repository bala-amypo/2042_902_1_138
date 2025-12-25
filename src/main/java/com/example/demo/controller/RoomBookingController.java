package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.RoomBooking;
import com.example.demo.service.RoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Room Bookings", description = "Room booking operations")
public class RoomBookingController {
    
    private final RoomBookingService roomBookingService;
    
    public RoomBookingController(RoomBookingService roomBookingService) {
        this.roomBookingService = roomBookingService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new booking")
    public ApiResponse createBooking(@RequestBody RoomBooking booking) {
        try {
            RoomBooking createdBooking = roomBookingService.createBooking(booking);
            return ApiResponse.success("Booking created successfully", createdBooking);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ApiResponse getBookingById(@PathVariable Long id) {
        try {
            RoomBooking booking = roomBookingService.getBookingById(id);
            return ApiResponse.success("Booking retrieved successfully", booking);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get bookings for guest")
    public ApiResponse getBookingsForGuest(@PathVariable Long guestId) {
        List<RoomBooking> bookings = roomBookingService.getBookingsForGuest(guestId);
        return ApiResponse.success("Bookings retrieved successfully", bookings);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update booking")
    public ApiResponse updateBooking(@PathVariable Long id, @RequestBody RoomBooking booking) {
        try {
            RoomBooking updatedBooking = roomBookingService.updateBooking(id, booking);
            return ApiResponse.success("Booking updated successfully", updatedBooking);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate booking")
    public ApiResponse deactivateBooking(@PathVariable Long id) {
        try {
            roomBookingService.deactivateBooking(id);
            return ApiResponse.success("Booking deactivated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}