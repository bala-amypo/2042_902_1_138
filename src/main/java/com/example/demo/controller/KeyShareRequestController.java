package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/key-share")
@Tag(name = "Key Sharing", description = "Key sharing operations")
public class KeyShareRequestController {
    
    private final KeyShareRequestService keyShareRequestService;
    
    public KeyShareRequestController(KeyShareRequestService keyShareRequestService) {
        this.keyShareRequestService = keyShareRequestService;
    }
    
    @PostMapping
    @Operation(summary = "Create key share request")
    public ResponseEntity<ApiResponse> createShareRequest(@RequestBody KeyShareRequest request) {
        try {
            KeyShareRequest createdRequest = keyShareRequestService.createShareRequest(request);
            return ResponseEntity.ok(ApiResponse.success("Key share request created successfully", createdRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get key share request by ID")
    public ResponseEntity<ApiResponse> getShareRequestById(@PathVariable Long id) {
        try {
            KeyShareRequest request = keyShareRequestService.getShareRequestById(id);
            return ResponseEntity.ok(ApiResponse.success("Key share request retrieved successfully", request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update key share request status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            KeyShareRequest updatedRequest = keyShareRequestService.updateStatus(id, status);
            return ResponseEntity.ok(ApiResponse.success("Key share request status updated successfully", updatedRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/shared-by/{guestId}")
    @Operation(summary = "Get requests shared by guest")
    public ResponseEntity<ApiResponse> getRequestsSharedBy(@PathVariable Long guestId) {
        List<KeyShareRequest> requests = keyShareRequestService.getRequestsSharedBy(guestId);
        return ResponseEntity.ok(ApiResponse.success("Shared requests retrieved successfully", requests));
    }
    
    @GetMapping("/shared-with/{guestId}")
    @Operation(summary = "Get requests shared with guest")
    public ResponseEntity<ApiResponse> getRequestsSharedWith(@PathVariable Long guestId) {
        List<KeyShareRequest> requests = keyShareRequestService.getRequestsSharedWith(guestId);
        return ResponseEntity.ok(ApiResponse.success("Received requests retrieved successfully", requests));
    }
}