package com.example.demo.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public CustomUserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // ✅ Dummy user (OK for tests & demo)
        return new CustomUserDetails(
                1L,
                username,
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
