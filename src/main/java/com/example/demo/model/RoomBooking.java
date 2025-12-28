package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class RoomBooking {

    @Id
    @GeneratedValue
    private Long id;

    private String roomNumber;
    private boolean active = true;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne
    private Guest guest;

    @ManyToMany
    private Set<Guest> roommates;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate d) { this.checkInDate = d; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate d) { this.checkOutDate = d; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public Set<Guest> getRoommates() { return roommates; }
    public void setRoommates(Set<Guest> roommates) { this.roommates = roommates; }
}
