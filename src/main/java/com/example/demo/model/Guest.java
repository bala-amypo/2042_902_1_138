package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Guest {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String fullName;
    private String phoneNumber;
    private Boolean active = true;
    private Boolean verified = false;
    private String role = "ROLE_USER";

    // getters & setters
}
