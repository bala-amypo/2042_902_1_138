package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.createGuest(guest);
    }

    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        return guestService.updateGuest(id, guest);
    }

    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @DeleteMapping("/{id}")
    public void deactivateGuest(@PathVariable Long id) {
        guestService.deactivateGuest(id);
    }
}
