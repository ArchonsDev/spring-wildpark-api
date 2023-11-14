package com.archons.springwildparkapi.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.AuthenticationRequest;
import com.archons.springwildparkapi.dto.AuthenticationResponse;
import com.archons.springwildparkapi.dto.RegisterRequest;
import com.archons.springwildparkapi.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {
    private final AuthenticationService authService;

    public AuthenticationControllerV1(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
