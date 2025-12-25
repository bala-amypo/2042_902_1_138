package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "key_share_requests")
public class KeyShareRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "digital_key_id")
    private DigitalKey digitalKey;
    
    @ManyToOne
    @JoinColumn(name = "shared_by_id")
    private Guest sharedBy;
    
    @ManyToOne
    @JoinColumn(name = "shared_with_id")
    private Guest sharedWith;
    
    private Timestamp shareStart;
    private Timestamp shareEnd;
    private String status = "PENDING";
    private Timestamp createdAt;
    
    public KeyShareRequest() {}
    
    public KeyShareRequest(DigitalKey digitalKey, Guest sharedBy, Guest sharedWith, Timestamp shareStart, Timestamp shareEnd, String status) {
        if (shareEnd != null && shareStart != null && !shareEnd.after(shareStart)) {
            throw new IllegalArgumentException("Share end time must be after share start time");
        }
        if (sharedBy != null && sharedWith != null && sharedBy.getId().equals(sharedWith.getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be the same guest");
        }
        this.digitalKey = digitalKey;
        this.sharedBy = sharedBy;
        this.sharedWith = sharedWith;
        this.shareStart = shareStart;
        this.shareEnd = shareEnd;
        this.status = status;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }
    
    public Guest getSharedBy() { return sharedBy; }
    public void setSharedBy(Guest sharedBy) { this.sharedBy = sharedBy; }
    
    public Guest getSharedWith() { return sharedWith; }
    public void setSharedWith(Guest sharedWith) { this.sharedWith = sharedWith; }
    
    public Timestamp getShareStart() { return shareStart; }
    public void setShareStart(Timestamp shareStart) { this.shareStart = shareStart; }
    
    public Timestamp getShareEnd() { return shareEnd; }
    public void setShareEnd(Timestamp shareEnd) { this.shareEnd = shareEnd; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}