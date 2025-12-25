package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class KeyShareRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp shareStart;
    private Timestamp shareEnd;
    private String status;

    @ManyToOne
    private Guest sharedBy;

    @ManyToOne
    private Guest sharedWith;

    public KeyShareRequest() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Timestamp getShareStart() { return shareStart; }
    public void setShareStart(Timestamp shareStart) { this.shareStart = shareStart; }

    public Timestamp getShareEnd() { return shareEnd; }
    public void setShareEnd(Timestamp shareEnd) { this.shareEnd = shareEnd; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Guest getSharedBy() { return sharedBy; }
    public void setSharedBy(Guest sharedBy) { this.sharedBy = sharedBy; }

    public Guest getSharedWith() { return sharedWith; }
    public void setSharedWith(Guest sharedWith) { this.sharedWith = sharedWith; }
}
