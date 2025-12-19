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
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    
    @Column(name = "expires_at")
    private Timestamp expiresAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    public Timestamp timestamp() {
        return this.createdAt;
    }
    
    public boolean isAfter(Timestamp other) {
        if (this.createdAt == null || other == null) return false;
        return this.createdAt.after(other);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Timestamp expiresAt) { this.expiresAt = expiresAt; }
}