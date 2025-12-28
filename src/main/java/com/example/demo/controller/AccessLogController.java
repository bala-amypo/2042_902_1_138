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
@RequestMapping("/api/logs")
@Tag(name = "Access Logs", description = "Access Logging")
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

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get logs for guest")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(@Parameter(description = "Guest ID") @PathVariable Long guestId) {
        return ResponseEntity.ok(accessLogService.getLogsForGuest(guestId));
    }

    @GetMapping("/key/{keyId}")
    @Operation(summary = "Get logs for specific key")
    public ResponseEntity<List<AccessLog>> getLogsForKey(@PathVariable Long keyId) {
        return ResponseEntity.ok(accessLogService.getLogsForKey(keyId));
    }
}