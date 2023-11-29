package com.archons.springwildparkapi.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.AuthenticationRequest;
import com.archons.springwildparkapi.dto.AuthenticationResponse;
import com.archons.springwildparkapi.dto.RegisterAccountRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {
    /*
     * This controller class handles all authentication related requests
     * 
     * Endpoints:
     * POST /api/v1/auth/register
     * POST /api/v1/auth/authenticate
     * 
     */
    private final AuthenticationService authService;

    public AuthenticationControllerV1(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterAccountRequest request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (DuplicateEntityException ex) {
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
