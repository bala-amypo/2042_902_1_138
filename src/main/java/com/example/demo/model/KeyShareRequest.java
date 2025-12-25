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
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest sharedBy;

    @ManyToOne
    private Guest sharedWith;

    private Timestamp shareStart;
    private Timestamp shareEnd;

    private String status = "PENDING";

    private Timestamp createdAt;

    public KeyShareRequest() {}

    public KeyShareRequest(DigitalKey digitalKey, Guest sharedBy,
                            Guest sharedWith, Timestamp shareStart,
                            Timestamp shareEnd, String status) {
        this.digitalKey = digitalKey;
        this.sharedBy = sharedBy;
        this.sharedWith = sharedWith;
        this.shareStart = shareStart;
        this.shareEnd = shareEnd;
        this.status = status;
    }

    @PrePersist
    public void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    // getters and setters
}
