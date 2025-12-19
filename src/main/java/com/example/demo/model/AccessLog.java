package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
public class AccessLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "access_time", nullable = false)
    private Timestamp accessTime;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    public Timestamp timestamp() {
        return this.accessTime != null ? this.accessTime : this.createdAt;
    }
    
    public boolean isAfter(Timestamp other) {
        Timestamp compareTime = this.accessTime != null ? this.accessTime : this.createdAt;
        if (compareTime == null || other == null) return false;
        return compareTime.after(other);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Timestamp getAccessTime() { return accessTime; }
    public void setAccessTime(Timestamp accessTime) { this.accessTime = accessTime; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}