package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/key-share")
public class KeyShareRequestController {

    private final KeyShareRequestService keyShareService;

    public KeyShareRequestController(KeyShareRequestService keyShareService) {
        this.keyShareService = keyShareService;
    }

    @PostMapping
    public KeyShareRequest shareKey(@RequestBody KeyShareRequest request) {
        return keyShareService.shareKey(request);
    }
}