package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "guests", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private String password;

    private Boolean verified = false;

    private Boolean active = true;

    private String role = "ROLE_USER";

    private Timestamp createdAt;

    public Guest() {}

    public Guest(String fullName, String email, String phoneNumber,
                 String password, Boolean verified,
                 Boolean active, String role, Timestamp createdAt) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.verified = verified;
        this.active = active;
        this.role = role;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    // getters and setters
}
