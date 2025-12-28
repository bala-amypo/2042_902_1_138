package com.example.demo.controller;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/keys")
public class DigitalKeyController {

    private final DigitalKeyService keyService;

    public DigitalKeyController(DigitalKeyService keyService) {
        this.keyService = keyService;
    }

    @PostMapping("/generate/{bookingId}")
    public DigitalKey generateKey(@PathVariable Long bookingId) {
        return keyService.generateKey(bookingId);
    }

    @GetMapping("/booking/{bookingId}")
    public DigitalKey getActiveKey(@PathVariable Long bookingId) {
        return keyService.getActiveKeyByBooking(bookingId);
    }

    @PutMapping("/deactivate/{keyId}")
    public void deactivateKey(@PathVariable Long keyId) {
        keyService.deactivateKey(keyId);
    }
}