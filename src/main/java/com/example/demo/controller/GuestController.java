package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guests", description = "Guest Management")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    @Operation(summary = "Get all guests")
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get guest by ID")
    public ResponseEntity<Guest> getGuestById(@Parameter(description = "Guest ID") @PathVariable Long id) {
        return ResponseEntity.ok(guestService.getGuestById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update guest details")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody Guest guestDetails) {
        return ResponseEntity.ok(guestService.updateGuest(id, guestDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate guest")
    public ResponseEntity<Void> deactivateGuest(@PathVariable Long id) {
        guestService.deactivateGuest(id);
        return ResponseEntity.noContent().build();
    }
}