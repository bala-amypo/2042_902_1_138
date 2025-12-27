


Geust.java
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

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean verified = false;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @Column(nullable = false)
    private Timestamp createdAt;

    public Guest() {}

    public Guest(String fullName, String email, String phoneNumber, String password, Boolean verified, Boolean active, String role, Timestamp createdAt) {
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

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}



KeyShareRequest.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "key_share_requests")
public class KeyShareRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "digital_key_id", nullable = false)
    private DigitalKey digitalKey;

    @ManyToOne
    @JoinColumn(name = "shared_by_id", nullable = false)
    private Guest sharedBy;

    @ManyToOne
    @JoinColumn(name = "shared_with_id", nullable = false)
    private Guest sharedWith;

    @Column(nullable = false)
    private Instant shareStart;

    @Column(nullable = false)
    private Instant shareEnd;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false)
    private Instant createdAt;

    public KeyShareRequest() {}

    public KeyShareRequest(DigitalKey digitalKey, Guest sharedBy, Guest sharedWith, Instant shareStart, Instant shareEnd, String status) {
        if (shareEnd != null && shareStart != null && !shareEnd.isAfter(shareStart)) {
            throw new IllegalArgumentException("Share end time must be after share start time");
        }
        if (sharedBy != null && sharedWith != null && sharedBy.getId().equals(sharedWith.getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be the same guest");
        }
        this.digitalKey = digitalKey;
        this.sharedBy = sharedBy;
        this.sharedWith = sharedWith;
        this.shareStart = shareStart;
        this.shareEnd = shareEnd;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DigitalKey getDigitalKey() { return digitalKey; }
    public void setDigitalKey(DigitalKey digitalKey) { this.digitalKey = digitalKey; }

    public Guest getSharedBy() { return sharedBy; }
    public void setSharedBy(Guest sharedBy) { this.sharedBy = sharedBy; }

    public Guest getSharedWith() { return sharedWith; }
    public void setSharedWith(Guest sharedWith) { this.sharedWith = sharedWith; }

    public Instant getShareStart() { return shareStart; }
    public void setShareStart(Instant shareStart) { this.shareStart = shareStart; }

    public Instant getShareEnd() { return shareEnd; }
    public void setShareEnd(Instant shareEnd) { this.shareEnd = shareEnd; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}



RoomBooking.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room_bookings")
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
        name = "booking_roommates",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> roommates = new HashSet<>();

    public RoomBooking() {}

    public RoomBooking(Guest guest, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate, Boolean active) {
        if (checkInDate != null && checkOutDate != null && !checkInDate.isBefore(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        this.guest = guest;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Set<Guest> getRoommates() { return roommates; }
    public void setRoommates(Set<Guest> roommates) { this.roommates = roommates; }
}