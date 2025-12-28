package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class AccessLog {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne private DigitalKey digitalKey;
    @ManyToOne private Guest guest;

    private Instant accessTime;
    private String result;

    // getters & setters
}
