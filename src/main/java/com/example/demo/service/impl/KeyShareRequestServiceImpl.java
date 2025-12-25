package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
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
        if (request.getShareEnd().before(request.getShareStart()) || 
            request.getShareEnd().equals(request.getShareStart())) {
            throw new IllegalArgumentException("Share end");
        }
        
        if (request.getSharedBy().getId().equals(request.getSharedWith().getId())) {
            throw new IllegalArgumentException("sharedBy and sharedWith");
        }
        
        // Verify digital key exists and is active
        DigitalKey digitalKey = digitalKeyRepository.findById(request.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        
        if (!digitalKey.getActive()) {
            throw new IllegalStateException("Key is not active");
        }
        
        // Verify guests exist
        Guest sharedBy = guestRepository.findById(request.getSharedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sharing guest not found"));
        
        Guest sharedWith = guestRepository.findById(request.getSharedWith().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiving guest not found"));
        
        request.setDigitalKey(digitalKey);
        request.setSharedBy(sharedBy);
        request.setSharedWith(sharedWith);
        request.setStatus("PENDING");
        
        return keyShareRequestRepository.save(request);
    }
    
    @Override
    public KeyShareRequest updateStatus(Long requestId, String status) {
        KeyShareRequest request = keyShareRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Key share request not found with id: " + requestId));
        
        request.setStatus(status);
        return keyShareRequestRepository.save(request);
    }
    
    @Override
    public KeyShareRequest getShareRequestById(Long id) {
        return keyShareRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Key share request not found with id: " + id));
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