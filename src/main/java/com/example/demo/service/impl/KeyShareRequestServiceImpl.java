package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.KeyShareRequestService;

import java.util.List;

public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository repo;
    private final DigitalKeyRepository keyRepo;
    private final GuestRepository guestRepo;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository r,
                                      DigitalKeyRepository k,
                                      GuestRepository g) {
        this.repo = r;
        this.keyRepo = k;
        this.guestRepo = g;
    }

    public KeyShareRequest createShareRequest(KeyShareRequest r) {
        if (r.getShareStart().isAfter(r.getShareEnd()))
            throw new IllegalArgumentException();

        if (r.getSharedBy().getId().equals(r.getSharedWith().getId()))
            throw new IllegalArgumentException();

        return repo.save(r);
    }

    public void revokeShareRequest(Long id) {
        KeyShareRequest r = repo.findById(id).orElseThrow();
        r.setActive(false);
    }

    public List<KeyShareRequest> getRequestsSharedBy(Long id) {
        return repo.findBySharedById(id);
    }

    public List<KeyShareRequest> getRequestsSharedWith(Long id) {
        return repo.findBySharedWithId(id);
    }
}
