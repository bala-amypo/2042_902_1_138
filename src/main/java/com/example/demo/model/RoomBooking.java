package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean active = true;

    @ManyToOne
    private Guest guest;

    @ManyToMany
    @JoinTable(
        name = "booking_roommates",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> roommates = new HashSet<>();
}