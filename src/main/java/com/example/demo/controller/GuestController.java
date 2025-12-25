package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest Management", description = "Guest CRUD operations")
public class GuestController {
    
    private final GuestService guestService;
    
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new guest")
    public ApiResponse createGuest(@RequestBody Guest guest) {
        try {
            Guest createdGuest = guestService.createGuest(guest);
            return ApiResponse.success("Guest created successfully", createdGuest);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get guest by ID")
    public ApiResponse getGuestById(@PathVariable Long id) {
        try {
            Guest guest = guestService.getGuestById(id);
            return ApiResponse.success("Guest retrieved successfully", guest);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping
    @Operation(summary = "Get all guests")
    public ApiResponse getAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        return ApiResponse.success("Guests retrieved successfully", guests);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update guest")
    public ApiResponse updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        try {
            Guest updatedGuest = guestService.updateGuest(id, guest);
            return ApiResponse.success("Guest updated successfully", updatedGuest);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate guest")
    public ApiResponse deactivateGuest(@PathVariable Long id) {
        try {
            guestService.deactivateGuest(id);
            return ApiResponse.success("Guest deactivated successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}