package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.RoomBookingService;

import java.util.List;

public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository repo;

    public RoomBookingServiceImpl(RoomBookingRepository repo) {
        this.repo = repo;
    }

    public RoomBooking createBooking(RoomBooking b) {
        if (b.getCheckInDate().isAfter(b.getCheckOutDate()))
            throw new IllegalArgumentException();
        return repo.save(b);
    }

    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return repo.findByGuestId(guestId);
    }

    public RoomBooking updateBooking(Long id, RoomBooking b) {
        RoomBooking existing = repo.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        existing.setRoomNumber(b.getRoomNumber());
        return repo.save(existing);
    }
}
