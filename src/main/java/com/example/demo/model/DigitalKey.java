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
    @JoinColumn(name = "booking_id")
    private RoomBooking booking;
    
    @Column(unique = true)
    private String keyValue;
    
    private Timestamp issuedAt;
    private Timestamp expiresAt;
    private Boolean active = true;
    
    public DigitalKey() {}
    
    public DigitalKey(RoomBooking booking, String keyValue, Timestamp issuedAt, Timestamp expiresAt, Boolean active) {
        if (expiresAt != null && issuedAt != null && expiresAt.before(issuedAt)) {
            throw new IllegalArgumentException("Expiration time must be after issuance time");
        }
        this.booking = booking;
        this.keyValue = keyValue;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.active = active;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public RoomBooking getBooking() { return booking; }
    public void setBooking(RoomBooking booking) { this.booking = booking; }
    
    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }
    
    public Timestamp getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Timestamp issuedAt) { this.issuedAt = issuedAt; }
    
    public Timestamp getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Timestamp expiresAt) { this.expiresAt = expiresAt; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}