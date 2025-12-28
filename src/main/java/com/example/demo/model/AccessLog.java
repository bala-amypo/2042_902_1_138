package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class AccessLog {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest guest;

    private Instant accessTime;
    private String result;

    public Long getId() { return id; }

    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public Instant getAccessTime() { return accessTime; }
    public void setAccessTime(Instant accessTime) { this.accessTime = accessTime; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}
