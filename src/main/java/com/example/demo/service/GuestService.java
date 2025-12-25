package com.example.demo.service;

import com.example.demo.model.Guest;
import java.util.List;

public interface GuestService {
    
    Guest createGuest(Guest guest);
    
    Guest updateGuest(Long id, Guest guest);
    
    Guest getGuestById(Long id);
    
    Guest getGuestByEmail(String email); // Add this method
    
    List<Guest> getAllGuests();
    
    void deactivateGuest(Long id);
}