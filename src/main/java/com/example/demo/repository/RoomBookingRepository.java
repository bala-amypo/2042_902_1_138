package com.example.demo.repository;

import com.example.demo.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    // 🔥 IMPORTANT FIX
    List<RoomBooking> findByGuest_Id(Long guestId);
}
