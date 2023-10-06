package com.archons.springwildparkapi.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationControllerV1 {
        private final AuthenticationService service;

        public AuthenticationControllerV1(AuthenticationService service) {
                this.service = service;
        }

        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(
                        @RequestBody RegisterRequest request) {
                return ResponseEntity.ok(service.register(request));
        }

        @PostMapping("/authenticate")
        public ResponseEntity<AuthenticationResponse> authenticate(
                        @RequestBody AuthenticationRequest request) {
                return ResponseEntity.ok(service.authenticate(request));
        }
}
