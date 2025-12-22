package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
@Tag(name = "Access Logs")
public class AccessLogController {
    
    private final AccessLogService accessLogService;
    
    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
    
    @PostMapping
    @Operation(summary = "Create access log")
    public ResponseEntity<AccessLog> createLog(@RequestBody AccessLog log) {
        return ResponseEntity.ok(accessLogService.createLog(log));
    }
    
    @GetMapping("/key/{keyId}")
    @Operation(summary = "Get logs for key")
    public ResponseEntity<List<AccessLog>> getLogsForKey(@Parameter(name = "keyId", description = "Key ID") @PathVariable Long keyId) {
        return ResponseEntity.ok(accessLogService.getLogsForKey(keyId));
    }
    
    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get logs for guest")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(@Parameter(name = "guestId", description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(accessLogService.getLogsForGuest(guestId));
    }
}