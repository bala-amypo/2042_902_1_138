package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "guests")
public class Guest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phoneNumber;
    
    private String password;
    
    private Boolean verified = false;
    
    private Boolean active = true;
    
    private String role = "ROLE_USER";
    
    @Column(name = "created_at")
    private Timestamp createdAt;
    
    @OneToMany(mappedBy = "guest")
    private Set<RoomBooking> bookings = new HashSet<>();
    
    @OneToMany(mappedBy = "sharedBy")
    private Set<KeyShareRequest> sharedRequests = new HashSet<>();
    
    @OneToMany(mappedBy = "sharedWith")
    private Set<KeyShareRequest> receivedRequests = new HashSet<>();
    
    @ManyToMany(mappedBy = "roommates")
    private Set<RoomBooking> roommateBookings = new HashSet<>();
    
    @OneToMany(mappedBy = "guest")
    private Set<AccessLog> accessLogs = new HashSet<>();
    
    public Guest() {}
    
    public Guest(String fullName, String email, String phoneNumber, String password, 
                 Boolean verified, Boolean active, String role, Timestamp createdAt) {
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
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }
    }
    
    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getFullName() { 
        return fullName; 
    }
    
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public Boolean getVerified() { 
        return verified; 
    }
    
    public void setVerified(Boolean verified) { 
        this.verified = verified; 
    }
    
    public Boolean getActive() { 
        return active; 
    }
    
    public void setActive(Boolean active) { 
        this.active = active; 
    }
    
    public String getRole() { 
        return role; 
    }
    
    public void setRole(String role) { 
        this.role = role; 
    }
    
    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }
}