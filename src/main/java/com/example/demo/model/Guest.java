package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;  // Keep this import
import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "guests")
public class Guest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private Boolean verified = false;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false)
    private String role = "ROLE_USER";
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;  //  Internal: Use Instant
    
    //  Keep Timestamp getter/setter for backward compatibility
    @Transient  // This field won't be stored in DB
    public Timestamp getCreatedAtTimestamp() {
        return Timestamp.from(createdAt);
    }
    
    @Transient
    public void setCreatedAtTimestamp(Timestamp timestamp) {
        this.createdAt = timestamp.toInstant();
    }
    
    // Add deprecated getter for Timestamp (for backward compatibility)
    @Deprecated
    public Timestamp getCreatedAt() {
        return Timestamp.from(createdAt);
    }
    
    @Deprecated
    public void setCreatedAt(Timestamp timestamp) {
        this.createdAt = timestamp.toInstant();
    }
    
    //  Add getter for Instant
    public Instant getCreatedAtInstant() {
        return createdAt;
    }
    
    public void setCreatedAtInstant(Instant instant) {
        this.createdAt = instant;
    }
    
    public Guest() {}
    
    //  Keep both constructors
    public Guest(String fullName, String email, String phoneNumber, String password, 
                 Boolean verified, Boolean active, String role) {
        this(fullName, email, phoneNumber, password, verified, active, role, null);
    }
    
    // Keep old constructor for backward compatibility
    public Guest(String fullName, String email, String phoneNumber, String password, 
                 Boolean verified, Boolean active, String role, Timestamp createdAt) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.verified = verified != null ? verified : false;
        this.active = active != null ? active : true;
        this.role = role != null ? role : "ROLE_USER";
        
        if (createdAt != null) {
            this.createdAt = createdAt.toInstant();
        }
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
    
    // Getters and Setters (standard ones)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}