package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String fullName;
    private String phoneNumber;

    private Boolean verified = false;
    private Boolean active = true;

    private String role = "ROLE_USER";

    // ✅ equals & hashCode REQUIRED for ManyToMany uniqueness
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
