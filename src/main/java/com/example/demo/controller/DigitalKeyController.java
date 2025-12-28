package com.example.demo.controller;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/keys")
@Tag(name = "Digital Keys", description = "Key Management")
public class DigitalKeyController {

    private final DigitalKeyService digitalKeyService;

    public DigitalKeyController(DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }

    @PostMapping("/generate/{bookingId}")
    @Operation(summary = "Generate key for booking")
    public ResponseEntity<DigitalKey> generateKey(@Parameter(description = "Booking ID") @PathVariable Long bookingId) {
        return ResponseEntity.ok(digitalKeyService.generateKey(bookingId));
    }

    @GetMapping("/active/{bookingId}")
    @Operation(summary = "Get active key for booking")
    public ResponseEntity<DigitalKey> getActiveKey(@PathVariable Long bookingId) {
        return ResponseEntity.ok(digitalKeyService.getActiveKeyForBooking(bookingId));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get keys for guest")
    public ResponseEntity<List<DigitalKey>> getKeysForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(digitalKeyService.getKeysForGuest(guestId));
    }
}