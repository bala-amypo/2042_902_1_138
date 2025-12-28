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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }

    public Instant getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public RoomBooking getBooking() { return booking; }
    public void setBooking(RoomBooking booking) { this.booking = booking; }
}
