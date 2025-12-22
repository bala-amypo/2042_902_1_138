package com.example.demo.repository;

import com.example.demo.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    
    List<RoomBooking> findByGuestId(Long guestId);
    List<RoomBooking> findByRoomNumber(String roomNumber);
    List<RoomBooking> findByRoomNumberAndStatusNot(String roomNumber, String status);
    List<RoomBooking> findByStatus(String status);
}