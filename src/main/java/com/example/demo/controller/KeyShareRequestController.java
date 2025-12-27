package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/key-share")
public class KeyShareRequestController {

    @GetMapping("/test")
    public String test() {
        return "KeyShare controller working";
    }
}
