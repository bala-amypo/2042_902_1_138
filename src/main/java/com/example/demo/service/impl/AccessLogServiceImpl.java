
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
import java.time.Instant;
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
        if (log.getAccessTime() != null && log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }
        
        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        
        // Check access permissions
        boolean hasAccess = false;
        String reason = "";
        
        if (!key.getActive()) {
            reason = "Key is inactive";
        } else if (key.getExpiresAt().isBefore(Instant.now())) {
            reason = "Key has expired";
        } else if (key.getBooking().getGuest().getId().equals(guest.getId())) {
            hasAccess = true;
            reason = "Booking owner access";
        } else {
            // Check for approved share requests
            List<KeyShareRequest> shareRequests = keyShareRequestRepository.findBySharedWithId(guest.getId());
            for (KeyShareRequest request : shareRequests) {
                if (request.getDigitalKey().getId().equals(key.getId()) && 
                    "APPROVED".equals(request.getStatus()) &&
                    log.getAccessTime().isAfter(request.getShareStart()) &&
                    log.getAccessTime().isBefore(request.getShareEnd())) {
                    hasAccess = true;
                    reason = "Shared access approved";
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
