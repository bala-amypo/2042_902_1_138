package com.example.demo.security;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GuestRepository repo;

    public CustomUserDetailsService(GuestRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Guest g = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new User(g.getEmail(), g.getPassword(),
                List.of(new SimpleGrantedAuthority(g.getRole())));
    }
}
