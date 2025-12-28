package com.example.demo.security;

import com.example.demo.model.Guest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final Guest guest;

    public CustomUserDetails(Guest guest) {
        this.guest = guest;
    }

    public Long getId() {
        return guest.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = guest.getRole() != null ? guest.getRole() : "ROLE_USER";
        return Collections.singletonList(new SimpleGrantedAuthority(role));
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
        return guest.getActive() != null ? guest.getActive() : true;
    }
}