package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp accessTime;
    private String result;

    @ManyToOne
    private Guest guest;

    public AccessLog() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Timestamp getAccessTime() { return accessTime; }
    public void setAccessTime(Timestamp accessTime) { this.accessTime = accessTime; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }
}
