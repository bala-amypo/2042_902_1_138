package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {
    
    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;
    private final KeyShareRequestRepository keyShareRequestRepository;
    
    public AccessLogServiceImpl(AccessLogRepository accessLogRepository, 
                              DigitalKeyRepository digitalKeyRepository, 
                              GuestRepository guestRepository, 
                              KeyShareRequestRepository keyShareRequestRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
        this.keyShareRequestRepository = keyShareRequestRepository;
    }
    
    @Override
    public AccessLog createLog(AccessLog log) {
        if (log.getAccessTime() != null && log.getAccessTime().after(new Timestamp(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }
        
        DigitalKey digitalKey = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        
        // Check access permissions
        boolean hasAccess = false;
        String reason = "";
        
        if (!digitalKey.getActive()) {
            reason = "Digital key is inactive";
        } else if (!guest.getActive()) {
            reason = "Guest is inactive";
        } else if (digitalKey.getBooking().getGuest().getId().equals(guest.getId())) {
            hasAccess = true;
            reason = "Booking owner access";
        } else {
            // Check for approved share requests
            List<KeyShareRequest> shareRequests = keyShareRequestRepository.findBySharedWithId(guest.getId());
            for (KeyShareRequest request : shareRequests) {
                if (request.getDigitalKey().getId().equals(digitalKey.getId()) && 
                    "APPROVED".equals(request.getStatus()) &&
                    log.getAccessTime().after(request.getShareStart()) &&
                    log.getAccessTime().before(request.getShareEnd())) {
                    hasAccess = true;
                    reason = "Approved share request access";
                    break;
                }
            }
            if (!hasAccess) {
                reason = "No valid access permission";
            }
        }
        
        log.setResult(hasAccess ? "SUCCESS" : "DENIED");
        log.setReason(reason);
        
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