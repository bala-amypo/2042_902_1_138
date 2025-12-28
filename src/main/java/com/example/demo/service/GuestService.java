package com.example.demo.service;

import com.example.demo.model.Guest;

public interface GuestService {
    Guest createGuest(Guest guest);
    Guest getGuestById(Long id);
    void deactivateGuest(Long id);
}
