package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room_bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;
    
    @Column(name = "room_number", nullable = false)
    private String roomNumber;
    
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;
    
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;
    
    private Boolean active = true;
    
    @ManyToMany
    @JoinTable(
        name = "booking_roommates",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> roommates = new HashSet<>();
    
    @OneToMany(mappedBy = "booking")
    private Set<DigitalKey> digitalKeys = new HashSet<>();
}