package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {
    
    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;
    
    public AccessLogServiceImpl(AccessLogRepository accessLogRepository,
                              DigitalKeyRepository digitalKeyRepository,
                              GuestRepository guestRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }
    
    @Override
    public AccessLog createLog(AccessLog log) {
        // Validate key exists
        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new IllegalArgumentException("Digital key not found"));
        
        // Validate guest exists  
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
        
        // Check if access time is in future
        if (log.getAccessTime() != null && 
            log.getAccessTime().after(new Timestamp(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }
        
        // Check key validity
        if (!key.getActive() || 
            log.getAccessTime().after(key.getExpiresAt()) ||
            log.getAccessTime().before(key.getIssuedAt())) {
            log.setResult("DENIED");
            log.setReason("Invalid or expired key");
        } else {
            log.setResult("SUCCESS");
            log.setReason("Access granted");
        }
        
        return accessLogRepository.save(log);
    }
    
    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return accessLogRepository.findByDigitalKeyId(keyId);
    }
    
    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return accessLogRepository.findByGuestId(guestId);
    }
}