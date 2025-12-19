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
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "share_start")
    private Timestamp shareStart;
    
    @Column(name = "share_end")
    private Timestamp shareEnd;
    
    @ManyToOne
    @JoinColumn(name = "shared_by_id")
    private Guest sharedBy;
    
    @ManyToOne
    @JoinColumn(name = "shared_with_id")
    private Guest sharedWith;
    
    @ManyToOne
    @JoinColumn(name = "digital_key_id")
    private DigitalKey digitalKey;
    
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    @Column(name = "requested_at")
    private Timestamp requestedAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
        if (requestedAt == null) {
            requestedAt = Timestamp.valueOf(LocalDateTime.now());
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
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getShareStart() {
        return shareStart;
    }
    
    public void setShareStart(Timestamp shareStart) {
        this.shareStart = shareStart;
    }
    
    public Timestamp getShareEnd() {
        return shareEnd;
    }
    
    public void setShareEnd(Timestamp shareEnd) {
        this.shareEnd = shareEnd;
    }
    
    public Guest getSharedBy() {
        return sharedBy;
    }
    
    public void setSharedBy(Guest sharedBy) {
        this.sharedBy = sharedBy;
    }
    
    public Guest getSharedWith() {
        return sharedWith;
    }
    
    public void setSharedWith(Guest sharedWith) {
        this.sharedWith = sharedWith;
    }
    
    public DigitalKey getDigitalKey() {
        return digitalKey;
    }
    
    public void setDigitalKey(DigitalKey digitalKey) {
        this.digitalKey = digitalKey;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getRequestedAt() {
        return requestedAt;
    }
    
    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }
}