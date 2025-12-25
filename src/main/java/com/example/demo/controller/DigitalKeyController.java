package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/digital-keys")
@Tag(name = "Digital Keys", description = "Digital key operations")
public class DigitalKeyController {
    
    private final DigitalKeyService digitalKeyService;
    
    public DigitalKeyController(DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }
    
    @PostMapping("/generate/{bookingId}")
    @Operation(summary = "Generate digital key for booking")
    public ApiResponse generateKey(@PathVariable Long bookingId) {
        try {
            DigitalKey digitalKey = digitalKeyService.generateKey(bookingId);
            return ApiResponse.success("Digital key generated successfully", digitalKey);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get digital key by ID")
    public ApiResponse getKeyById(@PathVariable Long id) {
        try {
            DigitalKey digitalKey = digitalKeyService.getKeyById(id);
            return ApiResponse.success("Digital key retrieved successfully", digitalKey);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get active key for booking")
    public ApiResponse getActiveKeyForBooking(@PathVariable Long bookingId) {
        try {
            DigitalKey digitalKey = digitalKeyService.getActiveKeyForBooking(bookingId);
            return ApiResponse.success("Active digital key retrieved successfully", digitalKey);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get keys for guest")
    public ApiResponse getKeysForGuest(@PathVariable Long guestId) {
        List<DigitalKey> keys = digitalKeyService.getKeysForGuest(guestId);
        return ApiResponse.success("Digital keys retrieved successfully", keys);
    }
}