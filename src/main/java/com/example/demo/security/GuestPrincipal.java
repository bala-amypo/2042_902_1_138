package com.example.demo.security;

import com.example.demo.model.Guest;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class GuestPrincipal implements UserDetails {

    private final Guest guest;

    public GuestPrincipal(Guest guest) {
        this.guest = guest;
    }

    public Long getId() {
        return guest.getId();
    }

    public String getEmail() {
        return guest.getEmail();
    }

    public String getRole() {
        return guest.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(guest.getRole()));
    }

    @Override
    public String getPassword() {
        return guest.getPassword();
    }

    @Override
    public String getUsername() {
        return guest.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return guest.getActive();
    }
}
