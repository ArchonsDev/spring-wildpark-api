package com.archons.springwildparkapi.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.reesponses.AuthenticationResponse;
import com.archons.springwildparkapi.dto.requests.AuthenticationRequest;
import com.archons.springwildparkapi.dto.requests.RegisterAccountRequest;
import com.archons.springwildparkapi.service.AccountService;

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
    private final AccountService accountService;

    public AuthenticationControllerV1(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterAccountRequest request)
            throws Exception {
        return ResponseEntity.ok(accountService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
            throws Exception {
        return ResponseEntity.ok(accountService.authenticate(request));
    }
}
