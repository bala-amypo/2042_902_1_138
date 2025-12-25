package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "guests")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }
    }
}