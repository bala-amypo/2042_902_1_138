package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {
    private final AccessLogRepository logRepository;
    private final DigitalKeyRepository keyRepository;
    
    public AccessLogServiceImpl(AccessLogRepository logRepository,
                               DigitalKeyRepository keyRepository) {
        this.logRepository = logRepository;
        this.keyRepository = keyRepository;
    }
    
    @Override
    public AccessLog createLog(AccessLog log) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        
        if (log.getAccessTime().after(now)) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }
        
        DigitalKey key = keyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
        
        if (!key.getActive()) {
            log.setResult("DENIED");
            log.setReason("Key is inactive");
        } else if (key.getExpiresAt().before(now)) {
            log.setResult("DENIED");
            log.setReason("Key has expired");
        } else if (log.getAccessTime().before(key.getIssuedAt())) {
            log.setResult("DENIED");
            log.setReason("Access before key issuance");
        } else {
            log.setResult("SUCCESS");
            log.setReason("Access granted");
        }
        
        return logRepository.save(log);
    }
    
    @Override
    public List<AccessLog> getLogsForKey(long keyId) {
        return logRepository.findByDigitalKeyId(keyId);
    }
    
    @Override
    public List<AccessLog> getLogsForGuest(long guestId) {
        return logRepository.findByGuestId(guestId);
    }
}