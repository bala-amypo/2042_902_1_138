package com.example.demo.repository;

import com.example.demo.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// RoomBookingRepository
public interface RoomBookingRepository extends JpaRepository<RoomBooking,Long> {
    List<RoomBooking> findByGuestId(Long id);
}
