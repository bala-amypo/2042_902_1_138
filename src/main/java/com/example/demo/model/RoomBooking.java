package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_bookings")
public class RoomBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    
    @Column(name = "check_in_time")
    private Timestamp checkInTime;
    
    @Column(name = "check_out_time")
    private Timestamp checkOutTime;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    public Timestamp timestamp() {
        return this.checkInTime != null ? this.checkInTime : this.createdAt;
    }
    
    public boolean isAfter(Timestamp other) {
        Timestamp compareTime = this.checkInTime != null ? this.checkInTime : this.createdAt;
        if (compareTime == null || other == null) return false;
        return compareTime.after(other);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getCheckInTime() { return checkInTime; }
    public void setCheckInTime(Timestamp checkInTime) { this.checkInTime = checkInTime; }
    
    public Timestamp getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(Timestamp checkOutTime) { this.checkOutTime = checkOutTime; }
}