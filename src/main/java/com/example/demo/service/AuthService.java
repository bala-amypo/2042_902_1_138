package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest request);
}