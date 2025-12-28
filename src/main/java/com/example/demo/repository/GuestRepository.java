package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Guest;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    boolean existsByEmail(String email);
    Optional<Guest> findByEmail(String email);
}
