package com.archons.springwildparkapi.auth;

import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.User;
import com.archons.springwildparkapi.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository repository, JwtService jwtService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        // Creates a new user and returns a JWT Token
        User user = new User();

        // Set fields
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        // Save user
        repository.save(user);

        // Create JWT Token
        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate a user from the provided email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Find user from DB
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate JWT Token
        String jtwToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jtwToken);
    }
}
