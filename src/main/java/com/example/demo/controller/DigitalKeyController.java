package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.DigitalKey;

@Repository
public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {
    // You can add custom query methods if needed
    // Example: Optional<DigitalKey> findByBookingId(Long bookingId);
}
