package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "digital_keys")
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoomBooking booking;

    @Column(unique = true)
    private String keyValue;

    private Timestamp issuedAt;

    private Timestamp expiresAt;

    private Boolean active = true;

    public DigitalKey() {}

    public DigitalKey(RoomBooking booking, String keyValue,
                      Timestamp issuedAt, Timestamp expiresAt,
                      Boolean active) {
        this.booking = booking;
        this.keyValue = keyValue;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.active = active;
    }

    // getters and setters
}
