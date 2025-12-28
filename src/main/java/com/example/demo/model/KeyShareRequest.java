package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class KeyShareRequest {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne private DigitalKey digitalKey;
    @ManyToOne private Guest sharedBy;
    @ManyToOne private Guest sharedWith;

    private Instant shareStart;
    private Instant shareEnd;

    // getters & setters
}
