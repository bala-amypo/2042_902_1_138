package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
}