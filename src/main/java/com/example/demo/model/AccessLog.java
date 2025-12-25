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
    private DigitalKey digitalKey;

    @ManyToOne
    private Guest guest;

    private Timestamp accessTime;

    private String result;

    private String reason;

    public AccessLog() {}

    public AccessLog(DigitalKey digitalKey, Guest guest,
                     Timestamp accessTime, String result, String reason) {
        this.digitalKey = digitalKey;
        this.guest = guest;
        this.accessTime = accessTime;
        this.result = result;
        this.reason = reason;
    }

    // getters and setters
}
