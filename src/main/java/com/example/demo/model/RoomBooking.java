package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class RoomBooking {

    @Id @GeneratedValue
    private Long id;

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean active = true;

    @ManyToOne
    private Guest guest;

    @ManyToMany
    private Set<Guest> roommates = new HashSet<>();

    // getters & setters
}
