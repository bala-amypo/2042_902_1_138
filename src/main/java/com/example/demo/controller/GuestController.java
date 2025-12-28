package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guests", description = "Guest management APIs")
public class GuestController {
    @Operation(summary = "Get guest by ID")
    public void getGuest(@Parameter(description = "ID of guest") Long id) {}
}