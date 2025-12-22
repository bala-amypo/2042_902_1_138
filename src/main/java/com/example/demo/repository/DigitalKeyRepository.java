package com.example.demo.repository;

import com.example.demo.model.DigitalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {
    
    Optional<DigitalKey> findByKeyValue(String keyValue);
    
    Optional<DigitalKey> findByBookingIdAndStatus(Long bookingId, String status);
    
    List<DigitalKey> findByBookingGuestId(Long guestId);
    
    List<DigitalKey> findByStatus(String status);
    
    List<DigitalKey> findByExpiryTimeBeforeAndStatus(LocalDateTime expiryTime, String status);
    
    @Query("SELECT dk FROM DigitalKey dk WHERE dk.booking.roomNumber = :roomNumber AND dk.status = 'ACTIVE'")
    List<DigitalKey> findActiveKeysByRoomNumber(@Param("roomNumber") String roomNumber);
    
    @Query("SELECT COUNT(dk) FROM DigitalKey dk WHERE dk.booking.id = :bookingId AND dk.status = 'ACTIVE'")
    Long countActiveKeysByBookingId(@Param("bookingId") Long bookingId);
}