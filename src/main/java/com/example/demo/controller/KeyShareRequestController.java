package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shares")
@Tag(name = "Key Sharing", description = "Key Share Management")
public class KeyShareRequestController {

    private final KeyShareRequestService shareService;

    public KeyShareRequestController(KeyShareRequestService shareService) {
        this.shareService = shareService;
    }

    @PostMapping
    @Operation(summary = "Create share request")
    public ResponseEntity<KeyShareRequest> createShare(@RequestBody KeyShareRequest request) {
        return ResponseEntity.ok(shareService.createShareRequest(request));
    }

    @PostMapping("/{id}/revoke")
    @Operation(summary = "Revoke share request")
    public ResponseEntity<Void> revokeShare(@PathVariable Long id) {
        shareService.revokeShareRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/shared-by/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getSharedBy(@PathVariable Long guestId) {
        return ResponseEntity.ok(shareService.getRequestsSharedBy(guestId));
    }

    @GetMapping("/shared-with/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getSharedWith(@PathVariable Long guestId) {
        return ResponseEntity.ok(shareService.getRequestsSharedWith(guestId));
    }
}