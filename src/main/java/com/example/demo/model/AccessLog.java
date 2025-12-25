package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "access_logs")
public class AccessLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "digital_key_id", nullable = false)
    private DigitalKey digitalKey;
    
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;
    
    @Column(name = "access_time", nullable = false)
    private Timestamp accessTime;
    
    @Column(nullable = false)
    private String result;
    
    private String reason;
    
    public AccessLog() {}
    
    public AccessLog(DigitalKey digitalKey, Guest guest, Timestamp accessTime, 
                    String result, String reason) {
        this.digitalKey = digitalKey;
        this.guest = guest;
        this.accessTime = accessTime;
        this.result = result;
        this.reason = reason;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }
    
    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }
    
    public Timestamp getAccessTime() { return accessTime; }
    public void setAccessTime(Timestamp accessTime) { this.accessTime = accessTime; }
    
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}