package com.example.demo.service.impl;

import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class AccessLogServiceImpl {
    
    private final AccessLogRepository accessLogRepo;
    private final DigitalKeyRepository digitalKeyRepo;
    private final KeyShareRequestRepository keyShareRequestRepo;
    
    public AccessLogServiceImpl(AccessLogRepository accessLogRepo,
                               DigitalKeyRepository digitalKeyRepo,
                               KeyShareRequestRepository keyShareRequestRepo) {
        this.accessLogRepo = accessLogRepo;
        this.digitalKeyRepo = digitalKeyRepo;
        this.keyShareRequestRepo = keyShareRequestRepo;
    }
}