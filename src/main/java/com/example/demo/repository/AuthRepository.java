package com.example.demo.auth.repository;

import com.example.demo.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByEmail(String email);
}
