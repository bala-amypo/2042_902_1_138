package com.example.demo.security;

import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GuestRepository repo;

    public CustomUserDetailsService(GuestRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Guest guest = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new GuestPrincipal(guest);
    }
}
