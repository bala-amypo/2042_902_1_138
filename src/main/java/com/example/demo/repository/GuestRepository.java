package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.example.demo.model.*;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    boolean existsByEmail(String email);
    Optional<Guest> findByEmail(String email);
}
