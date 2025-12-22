package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_keys")
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_value", nullable = false, unique = true)
    private String keyValue;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private RoomBooking booking;

    @Column(name = "issue_time", nullable = false)
    private LocalDateTime issueTime;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    // Constructors
    public DigitalKey() {}

    public DigitalKey(String keyValue, RoomBooking booking) {
        this.keyValue = keyValue;
        this.booking = booking;
        this.issueTime = LocalDateTime.now();
        this.expiryTime = booking.getCheckOutDate();
    }

    // Getters and Setters
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

    public RoomBooking getBooking() {
        return booking;
    }

    public void setBooking(RoomBooking booking) {
        this.booking = booking;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(LocalDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper method to check if key is active
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }

    // Helper method to check if key is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryTime);
    }

    // Helper method to check if key is valid
    public boolean isValid() {
        return isActive() && !isExpired();
    }
}