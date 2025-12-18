package com.example.demo.security;

import com.example.demo.model.Guest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class GuestPrincipal implements UserDetails {
    
    private final Guest guest;
    
    public GuestPrincipal(Guest guest) {
        this.guest = guest;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + guest.getRole()));
    }
    
    @Override
    public String getPassword() {
        return guest.getPassword();
    }
    
    @Override
    public String getUsername() {
        return guest.getEmail();
    }
    
    public Long getId() {
        return guest.getId();
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
        return guest.getActive() != null && guest.getActive();
    }
}