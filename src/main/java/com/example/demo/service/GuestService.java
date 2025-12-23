package com.example.demo.service;

import com.example.demo.model.Guest;

import java.util.List;

public interface GuestService {

    // AuthController
    Guest createGuest(Guest guest);
    Guest getGuestByEmail(String email);

    // GuestController
    Guest getGuestById(Long id);
    List<Guest> getAllGuests();
    Guest updateGuest(Long id, Guest guest);
    void deactivateGuest(Long id);
}
