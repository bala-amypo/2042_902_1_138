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
    
    private final KeyShareRequestRepository keyShareRequestRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;
    
    public KeyShareRequestServiceImpl(KeyShareRequestRepository keyShareRequestRepository, 
                                    DigitalKeyRepository digitalKeyRepository, 
                                    GuestRepository guestRepository) {
        this.keyShareRequestRepository = keyShareRequestRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
    }
    
    @Override
    public KeyShareRequest createShareRequest(KeyShareRequest request) {
        if (request.getShareEnd() != null && request.getShareStart() != null && 
            !request.getShareEnd().after(request.getShareStart())) {
            throw new IllegalArgumentException("Share end time must be after share start time");
        }
        
        if (request.getSharedBy() != null && request.getSharedWith() != null && 
            request.getSharedBy().getId().equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith cannot be the same guest");
        }
        
        // Verify entities exist
        digitalKeyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        guestRepository.findById(request.getSharedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("SharedBy guest not found"));
        guestRepository.findById(request.getSharedWith().getId())
                .orElseThrow(() -> new ResourceNotFoundException("SharedWith guest not found"));
        
        request.setStatus("PENDING");
        return keyShareRequestRepository.save(request);
    }
    
    @Override
    public KeyShareRequest updateStatus(Long requestId, String status) {
        KeyShareRequest request = keyShareRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Share request not found"));
        request.setStatus(status);
        return keyShareRequestRepository.save(request);
    }
    
    @Override
    public KeyShareRequest getShareRequestById(Long id) {
        return keyShareRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Share request not found"));
    }
    
    @Override
    public List<KeyShareRequest> getRequestsSharedBy(Long guestId) {
        return keyShareRequestRepository.findBySharedById(guestId);
    }
    
    @Override
    public List<KeyShareRequest> getRequestsSharedWith(Long guestId) {
        return keyShareRequestRepository.findBySharedWithId(guestId);
    }
}