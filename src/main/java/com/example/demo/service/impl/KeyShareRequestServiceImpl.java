package com.example.demo.service.impl;

import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository repo;
    private final DigitalKeyRepository digitalKeyRepo;
    private final GuestRepository guestRepo;

    // ✅ CONSTRUCTOR MUST MATCH TEST FILE
    public KeyShareRequestServiceImpl(
            KeyShareRequestRepository repo,
            DigitalKeyRepository digitalKeyRepo,
            GuestRepository guestRepo) {
        this.repo = repo;
        this.digitalKeyRepo = digitalKeyRepo;
        this.guestRepo = guestRepo;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {

        if (request.getShareEnd().isBefore(request.getShareStart())) {
            throw new IllegalArgumentException("Share end before start");
        }

        if (request.getSharedBy().getId()
                .equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith same");
        }

        DigitalKey key = digitalKeyRepo
                .findById(request.getDigitalKey().getId())
                .orElseThrow();

        Guest sharedWith = guestRepo
                .findById(request.getSharedWith().getId())
                .orElseThrow();

        if (!sharedWith.getActive() || !sharedWith.getVerified()) {
            throw new IllegalStateException("Guest not eligible");
        }

        return repo.save(request);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return repo.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return repo.findBySharedWithId(guestId);
    }
}
