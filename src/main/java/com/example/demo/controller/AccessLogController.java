package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @PostMapping
    public AccessLog logAccess(@RequestBody AccessLog accessLog) {
        return accessLogService.logAccess(accessLog);
    }
}
