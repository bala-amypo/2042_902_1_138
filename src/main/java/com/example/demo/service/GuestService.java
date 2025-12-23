package com.example.demo.service;

import com.example.demo.model.Guest;

public interface GuestService {

    Guest createGuest(Guest guest);

    Guest getGuestByEmail(String email);
}
