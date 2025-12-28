package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class DigitalKey {

    @Id @GeneratedValue
    private Long id;

    private String keyValue;
    private Instant issuedAt;
    private Instant expiresAt;
    private Boolean active = true;

    @ManyToOne
    private RoomBooking booking;

    // getters & setters
}
