package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import com.example.demo.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
@Tag(name = "Guest Management")
public class GuestController {

    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;

    public GuestController(GuestService guestService,
                           JwtTokenProvider jwtTokenProvider) {
        this.guestService = guestService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get guest by ID")
    public ResponseEntity<ApiResponse> getGuestById(@PathVariable Long id) {
        Guest guest = guestService.getGuestById(id);
        if (guest == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Guest not found"));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Guest fetched", guest));
    }

    @GetMapping
    @Operation(summary = "Get all guests")
    public ResponseEntity<ApiResponse> getAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        return ResponseEntity.ok(new ApiResponse(true, "All guests fetched", guests));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update guest by ID")
    public ResponseEntity<ApiResponse> updateGuest(@PathVariable Long id,
                                                   @RequestBody Guest updatedGuest) {
        Guest guest = guestService.updateGuest(id, updatedGuest);
        if (guest == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Guest not found"));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Guest updated", guest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate guest by ID")
    public ResponseEntity<ApiResponse> deactivateGuest(@PathVariable Long id) {
        boolean deactivated = guestService.deactivateGuest(id);
        if (!deactivated) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Guest not found or already deactivated"));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Guest deactivated"));
    }

    @PostMapping
    @Operation(summary = "Create new guest")
    public ResponseEntity<ApiResponse> createGuest(@RequestBody Guest guest) {
        Guest created = guestService.createGuest(guest);
        return ResponseEntity.ok(new ApiResponse(true, "Guest created", created));
    }
}
