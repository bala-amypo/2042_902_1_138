package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
@Tag(name = "Access Logs", description = "Access log operations")
public class AccessLogController {
    
    private final AccessLogService accessLogService;
    
    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
    
    @PostMapping
    @Operation(summary = "Create access log")
    public ApiResponse createLog(@RequestBody AccessLog log) {
        try {
            AccessLog createdLog = accessLogService.createLog(log);
            return ApiResponse.success("Access log created successfully", createdLog);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @GetMapping("/key/{keyId}")
    @Operation(summary = "Get access logs for key")
    public ApiResponse getLogsForKey(@PathVariable Long keyId) {
        List<AccessLog> logs = accessLogService.getLogsForKey(keyId);
        return ApiResponse.success("Access logs retrieved successfully", logs);
    }
    
    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get access logs for guest")
    public ApiResponse getLogsForGuest(@PathVariable Long guestId) {
        List<AccessLog> logs = accessLogService.getLogsForGuest(guestId);
        return ApiResponse.success("Access logs retrieved successfully", logs);
    }
}