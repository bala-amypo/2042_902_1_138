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
    private Guest guest;

    private String roomNumber;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Boolean active = true;

    @ManyToMany
    private Set<Guest> roommates = new HashSet<>();

    public RoomBooking() {}

    public RoomBooking(Guest guest, String roomNumber,
                       LocalDate checkInDate, LocalDate checkOutDate,
                       Boolean active) {
        this.guest = guest;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.active = active;
    }

    // getters and setters
}
