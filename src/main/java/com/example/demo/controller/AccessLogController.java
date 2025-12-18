package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {
    private final AccessLogService accessLogService;
    
    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
    
    @PostMapping("/")
    public ResponseEntity<AccessLog> createLog(@RequestBody AccessLog log) {
        return ResponseEntity.ok(accessLogService.createLog(log));
    }
    
    @GetMapping("/key/{keyId}")
    public ResponseEntity<List<AccessLog>> getLogsForKey(@PathVariable long keyId) {
        return ResponseEntity.ok(accessLogService.getLogsForKey(keyId));
    }
    
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(@PathVariable long guestId) {
        return ResponseEntity.ok(accessLogService.getLogsForGuest(guestId));
    }
}