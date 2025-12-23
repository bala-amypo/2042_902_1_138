package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "access_logs")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date loginTime;

    @ManyToOne
    @JoinColumn(name = "guest_id") // foreign key column
    private Guest guest;

    // getters & setters
}
