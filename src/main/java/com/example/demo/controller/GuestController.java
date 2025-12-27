package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    @GetMapping
    public List<Guest> all() {
        return service.getAllGuests();
    }

    @GetMapping("/{id}")
    public Guest one(@PathVariable Long id) {
        return service.getGuestById(id);
    }
}
