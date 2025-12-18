package com.example.demo.service.impl;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

@Service
public class KeyShareRequestServiceImpl implements KeyShareRequestService {
    private final KeyShareRequestRepository requestRepository;
    private final DigitalKeyRepository keyRepository;
    private final GuestRepository guestRepository;
    
    public KeyShareRequestServiceImpl(KeyShareRequestRepository requestRepository,
                                     DigitalKeyRepository keyRepository,
                                     GuestRepository guestRepository) {
        this.requestRepository = requestRepository;
        this.keyRepository = keyRepository;
        this.guestRepository = guestRepository;
    }
    
    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {
        if (request.getShareEnd().before(request.getShareStart())) {
            throw new IllegalArgumentException("Share end must be after start");
        }
        
        if (request.getSharedBy().getId().equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be same");
        }
        
        DigitalKey key = keyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
        
        if (!key.getActive()) {
            throw new IllegalStateException("Key is not active");
        }
        
        request.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        request.setStatus("PENDING");
        
        return requestRepository.save(request);
    }
    
    @Override
    public KeyShareRequest updateStatus(long requestId, String status) {
        KeyShareRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        
        if (!List.of("PENDING", "APPROVED", "REJECTED").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        
        request.setStatus(status);
        return requestRepository.save(request);
    }
    
    @Override
    public KeyShareRequest getShareRequestById(long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }
    
    @Override
    public List<KeyShareRequest> getRequestsSharedBy(long guestId) {
        return requestRepository.findBySharedById(guestId);
    }
    
    @Override
    public List<KeyShareRequest> getRequestsSharedWith(long guestId) {
        return requestRepository.findBySharedWithId(guestId);
    }
}