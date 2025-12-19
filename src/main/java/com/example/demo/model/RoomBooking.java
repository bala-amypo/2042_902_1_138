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
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "check_in_date")
    private Timestamp checkInDate;
    
    @Column(name = "check_out_date")
    private Timestamp checkOutDate;
    
    @Column(name = "check_in_time")
    private Timestamp checkInTime;
    
    @Column(name = "check_out_time")
    private Timestamp checkOutTime;
    
    @Column(name = "created_at")
    private Timestamp createdAt;
    
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
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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
    
    public Timestamp getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(Timestamp checkInDate) {
        this.checkInDate = checkInDate;
    }
    
    public Timestamp getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(Timestamp checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public Timestamp getCheckInTime() {
        return checkInTime;
    }
    
    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }
    
    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }
    
    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}