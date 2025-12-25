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
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (log.getAccessTime().after(now)) {
            throw new IllegalArgumentException("future");
        }
        
        DigitalKey digitalKey = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        
        if (!digitalKey.getActive()) {
            log.setResult("DENIED");
            log.setReason("Key is not active");
            return accessLogRepository.save(log);
        }
        
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        
        if (!guest.getActive()) {
            log.setResult("DENIED");
            log.setReason("Guest is not active");
            return accessLogRepository.save(log);
        }
        
        if (digitalKey.getBooking().getGuest().getId().equals(guest.getId())) {
            log.setResult("SUCCESS");
            log.setReason("Booking owner");
            return accessLogRepository.save(log);
        }
        
        boolean isRoommate = digitalKey.getBooking().getRoommates().stream()
                .anyMatch(roommate -> roommate.getId().equals(guest.getId()));
        
        if (isRoommate) {
            log.setResult("SUCCESS");
            log.setReason("Roommate");
            return accessLogRepository.save(log);
        }
        
        List<KeyShareRequest> shareRequests = keyShareRequestRepository.findBySharedWithId(guest.getId());
        boolean hasValidShare = shareRequests.stream()
                .anyMatch(request -> 
                    request.getDigitalKey().getId().equals(digitalKey.getId()) &&
                    request.getStatus().equals("APPROVED") &&
                    log.getAccessTime().after(request.getShareStart()) &&
                    log.getAccessTime().before(request.getShareEnd()));
        
        if (hasValidShare) {
            log.setResult("SUCCESS");
            log.setReason("Approved share request");
            return accessLogRepository.save(log);
        }
        
        log.setResult("DENIED");
        log.setReason("No valid access rights");
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