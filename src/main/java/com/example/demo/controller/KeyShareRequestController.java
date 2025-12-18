package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/key-share")
public class KeyShareRequestController {
    private final KeyShareRequestService keyShareRequestService;
    
    public KeyShareRequestController(KeyShareRequestService keyShareRequestService) {
        this.keyShareRequestService = keyShareRequestService;
    }
    
    @PostMapping("/")
    public ResponseEntity<KeyShareRequest> createShareRequest(@RequestBody KeyShareRequest request) {
        return ResponseEntity.ok(keyShareRequestService.createShareRequest(request));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<KeyShareRequest> updateStatus(@PathVariable long id, @RequestBody String status) {
        return ResponseEntity.ok(keyShareRequestService.updateStatus(id, status));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<KeyShareRequest> getShareRequest(@PathVariable long id) {
        return ResponseEntity.ok(keyShareRequestService.getShareRequestById(id));
    }
    
    @GetMapping("/shared-by/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedBy(@PathVariable long guestId) {
        return ResponseEntity.ok(keyShareRequestService.getRequestsSharedBy(guestId));
    }
    
    @GetMapping("/shared-with/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedWith(@PathVariable long guestId) {
        return ResponseEntity.ok(keyShareRequestService.getRequestsSharedWith(guestId));
    }
}