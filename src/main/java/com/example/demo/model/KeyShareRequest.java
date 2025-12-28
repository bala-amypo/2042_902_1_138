package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class KeyShareRequest {

    @Id
    @GeneratedValue
    private Long id;

    private boolean active = true;
    private Instant shareStart;
    private Instant shareEnd;

    @ManyToOne
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest sharedBy;

    @ManyToOne
    private Guest sharedWith;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Instant getShareStart() { return shareStart; }
    public void setShareStart(Instant shareStart) { this.shareStart = shareStart; }

    public Instant getShareEnd() { return shareEnd; }
    public void setShareEnd(Instant shareEnd) { this.shareEnd = shareEnd; }

    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getSharedBy() { return sharedBy; }
    public void setSharedBy(Guest sharedBy) { this.sharedBy = sharedBy; }

    public Guest getSharedWith() { return sharedWith; }
    public void setSharedWith(Guest sharedWith) { this.sharedWith = sharedWith; }
}
