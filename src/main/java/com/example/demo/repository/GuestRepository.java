package com.example.demo.repository;

import com.example.demo.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    
    Optional<Guest> findByEmail(String email);
    
    boolean existsByEmail(String email);
}