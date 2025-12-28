package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest guest;

    private Instant accessTime;

    private String result;   // SUCCESS / DENIED
    private String reason;   // reason for denial or success

    // ---------------- getters & setters ----------------

    public Long getId() {
        return id;
    }

    public DigitalKey getDigitalKey() {
        return digitalKey;
    }

    public void setDigitalKey(DigitalKey digitalKey) {
        this.digitalKey = digitalKey;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Instant getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Instant accessTime) {
        this.accessTime = accessTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // ✅ THIS IS THE MISSING PART
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
