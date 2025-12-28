package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
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
        if (log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }

        boolean granted = false;
        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId()).orElse(null);
        Guest guest = guestRepository.findById(log.getGuest().getId()).orElse(null);

        if (key != null && Boolean.TRUE.equals(key.getActive())) {
            if (key.getBooking().getGuest().getId().equals(guest.getId())) {
                granted = true;
            } else {
                List<KeyShareRequest> shares = keyShareRequestRepository.findBySharedWithId(guest.getId());
                for (KeyShareRequest share : shares) {
                    if (share.getDigitalKey().getId().equals(key.getId()) && share.isActive()) {
                        granted = true;
                        break;
                    }
                }
            }
        }

        log.setResult(granted ? "SUCCESS" : "DENIED");
        return accessLogRepository.save(log);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return accessLogRepository.findByGuestId(guestId);
    }

    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return accessLogRepository.findByDigitalKeyId(keyId);
    }
}