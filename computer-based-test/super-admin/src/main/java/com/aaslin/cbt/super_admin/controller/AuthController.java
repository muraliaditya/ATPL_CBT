package com.aaslin.cbt.super_admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.super_admin.dto.CreateUserRequest;
import com.aaslin.cbt.super_admin.dto.CreateUserResponse;
import com.aaslin.cbt.super_admin.dto.LoginRequest;
import com.aaslin.cbt.super_admin.dto.LoginResponse;
import com.aaslin.cbt.super_admin.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/create-superadmin")
    public ResponseEntity<CreateUserResponse> createSuperAdmin(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(201).body(authService.createSuperAdmin(request));
    }

    @PostMapping("/create-developer")
    public ResponseEntity<CreateUserResponse> createDeveloper(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(201).body(authService.createDeveloper(request));
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken));
    }
}