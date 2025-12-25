package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
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
    private boolean active;

    // ----- GETTERS -----
    public Long getId() {
        return id;
    }

    public RoomBooking getBooking() {
        return booking;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public Timestamp getIssuedAt() {
        return issuedAt;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    // ----- SETTERS -----
    public void setId(Long id) {
        this.id = id;
    }

    public void setBooking(RoomBooking booking) {
        this.booking = booking;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public void setIssuedAt(Timestamp issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
