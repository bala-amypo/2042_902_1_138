package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {

    private final KeyShareRequestRepository repository;
    private final DigitalKeyRepository keyRepository;
    private final GuestRepository guestRepository;

    public KeyShareRequestServiceImpl(KeyShareRequestRepository repository, 
                                      DigitalKeyRepository keyRepository, 
                                      GuestRepository guestRepository) {
        this.repository = repository;
        this.keyRepository = keyRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest req) {
        // Validations
        if (req.getShareEnd().isBefore(req.getShareStart())) {
            throw new IllegalArgumentException("Share end date must be after start date");
        }
        if (req.getSharedBy().getId().equals(req.getSharedWith().getId())) {
            throw new IllegalArgumentException("Cannot share key with yourself (sharedBy and sharedWith are same)");
        }

        // Ensure entities exist (Simulated check)
        keyRepository.findById(req.getDigitalKey().getId());
        guestRepository.findById(req.getSharedBy().getId());
        guestRepository.findById(req.getSharedWith().getId());

        return repository.save(req);
    }

    @Override
    public void revokeShareRequest(Long requestId) {
        KeyShareRequest req = repository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        req.setActive(false);
        repository.save(req);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return repository.findBySharedById(guestId);
    }

    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return repository.findBySharedWithId(guestId);
    }
}