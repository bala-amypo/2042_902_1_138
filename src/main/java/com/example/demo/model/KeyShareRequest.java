package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "key_share_requests")
public class KeyShareRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    
    @Column(name = "requested_at")
    private Timestamp requestedAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    public Timestamp timestamp() {
        return this.requestedAt != null ? this.requestedAt : this.createdAt;
    }
    
    public boolean isAfter(Timestamp other) {
        Timestamp compareTime = this.requestedAt != null ? this.requestedAt : this.createdAt;
        if (compareTime == null || other == null) return false;
        return compareTime.after(other);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getRequestedAt() { return requestedAt; }
    public void setRequestedAt(Timestamp requestedAt) { this.requestedAt = requestedAt; }
}