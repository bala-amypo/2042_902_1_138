package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "auth")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String token;

    private Instant issuedAt;

    private Instant expiresAt;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }
 
    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
 
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Guest getGuest() {
        return guest;
    }
 
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
