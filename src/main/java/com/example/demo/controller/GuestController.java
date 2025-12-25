package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest Management")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public Guest create(@RequestBody Guest guest) {
        return guestService.createGuest(guest);
    }

    @GetMapping("/{id}")
    public Guest get(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }
}
