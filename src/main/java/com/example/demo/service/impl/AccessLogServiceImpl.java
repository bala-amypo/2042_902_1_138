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

    public AccessLogServiceImpl(
            AccessLogRepository accessLogRepository,
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

        // 1. future time check
        if (log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("future");
        }

        // 2. fetch key
        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));

        // 3. inactive key → DENIED
        if (!Boolean.TRUE.equals(key.getActive())) {
            log.setResult("DENIED");
            return accessLogRepository.save(log);
        }

        // 4. fetch guest
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        // 5. booking owner → SUCCESS
        if (key.getBooking() != null
                && key.getBooking().getGuest() != null
                && key.getBooking().getGuest().getId().equals(guest.getId())) {

            log.setResult("SUCCESS");
            return accessLogRepository.save(log);
        }

        // 6. shared access (NO status check)
        List<KeyShareRequest> shares =
                keyShareRequestRepository.findBySharedWithId(guest.getId());

        Instant now = Instant.now();
        for (KeyShareRequest req : shares) {
            if (req.getDigitalKey().getId().equals(key.getId())
                    && !now.isBefore(req.getShareStart())
                    && !now.isAfter(req.getShareEnd())) {

                log.setResult("SUCCESS");
                return accessLogRepository.save(log);
            }
        }

        // 7. default deny
        log.setResult("DENIED");
        return accessLogRepository.save(log);
    }

    @Override
    public AccessLog logAccess(AccessLog log) {
        return createLog(log);
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
