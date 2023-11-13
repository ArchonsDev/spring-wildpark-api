package com.archons.springwildparkapi.service;

import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.archons.springwildparkapi.auth.AuthenticationRequest;
import com.archons.springwildparkapi.auth.AuthenticationResponse;
import com.archons.springwildparkapi.auth.RegisterRequest;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AccountRepository accountRepository, JwtService jwtService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        // Creates a new user and returns a JWT Token
        AccountEntity account = new AccountEntity();

        // Set fields
        account.setFirstname(request.getFirstname());
        account.setLastname(request.getLastname());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole(Role.USER);

        // Save user
        accountRepository.save(account);

        // Create JWT Token
        String jwtToken = jwtService.generateToken(account);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate a user from the provided email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Find user from DB
        AccountEntity account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate JWT Token
        String jtwToken = jwtService.generateToken(account);

        return new AuthenticationResponse(jtwToken);
    }
}
