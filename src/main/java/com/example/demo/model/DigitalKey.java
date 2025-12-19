package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_keys")
public class DigitalKey {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "key_value")
    private String keyValue;
    
    @Column(name = "active")
    private boolean active;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private RoomBooking booking;
    
    @Column(name = "issued_at")
    private Timestamp issuedAt;
    
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    @Column(name = "expires_at")
    private Timestamp expiresAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
        if (issuedAt == null) {
            issuedAt = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    public Timestamp timestamp() {
        return this.createdAt;
    }
    
    public boolean isAfter(Timestamp other) {
        if (this.createdAt == null || other == null) return false;
        return this.createdAt.after(other);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getKeyValue() {
        return keyValue;
    }
    
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
    
    public boolean getActive() {
        return active;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public RoomBooking getBooking() {
        return booking;
    }
    
    public void setBooking(RoomBooking booking) {
        this.booking = booking;
    }
    
    public Timestamp getIssuedAt() {
        return issuedAt;
    }
    
    public void setIssuedAt(Timestamp issuedAt) {
        this.issuedAt = issuedAt;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }
}