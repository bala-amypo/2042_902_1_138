package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.AccessLogService;

import java.time.Instant;
import java.util.List;

public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository logRepo;
    private final DigitalKeyRepository keyRepo;
    private final GuestRepository guestRepo;
    private final KeyShareRequestRepository shareRepo;

    public AccessLogServiceImpl(AccessLogRepository l, DigitalKeyRepository k,
                                GuestRepository g, KeyShareRequestRepository s) {
        this.logRepo = l;
        this.keyRepo = k;
        this.guestRepo = g;
        this.shareRepo = s;
    }

    public AccessLog createLog(AccessLog l) {
        if (l.getAccessTime().isAfter(Instant.now()))
            throw new IllegalArgumentException();

        DigitalKey k = keyRepo.findById(l.getDigitalKey().getId()).orElse(null);
        Guest g = guestRepo.findById(l.getGuest().getId()).orElse(null);

        if (k != null && k.isActive())
            l.setResult("SUCCESS");
        else
            l.setResult("DENIED");

        return logRepo.save(l);
    }

    public List<AccessLog> getLogsForGuest(Long guestId) {
        return logRepo.findByGuestId(guestId);
    }

    public List<AccessLog> getLogsForKey(Long keyId) {
        return logRepo.findByDigitalKeyId(keyId);
    }
}
