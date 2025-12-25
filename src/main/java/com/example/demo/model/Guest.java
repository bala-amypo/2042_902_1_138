package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<RoomBooking> bookings;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<RoomBooking> getBookings() { return bookings; }
    public void setBookings(List<RoomBooking> bookings) { this.bookings = bookings; }
}
