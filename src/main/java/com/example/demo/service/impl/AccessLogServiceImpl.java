package com.example.demo.service.impl;

import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AccessLogServiceImpl {
    
    private final AccessLogRepository accessLogRepo;
    private final DigitalKeyRepository digitalKeyRepo;
    private final GuestRepository guestRepo;
    private final KeyShareRepository keyShareRepo;
    
    public AccessLogServiceImpl(AccessLogRepository accessLogRepo,
                               DigitalKeyRepository digitalKeyRepo,
                               GuestRepository guestRepo,
                               KeyShareRepository keyShareRepo) {
        this.accessLogRepo = accessLogRepo;
        this.digitalKeyRepo = digitalKeyRepo;
        this.guestRepo = guestRepo;
        this.keyShareRepo = keyShareRepo;
    }
}