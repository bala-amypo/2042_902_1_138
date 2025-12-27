package com.example.demo.repository;

import com.example.demo.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    // 🔥 FIXED QUERY
    List<RoomBooking> findByGuest_Id(Long guestId);
}
