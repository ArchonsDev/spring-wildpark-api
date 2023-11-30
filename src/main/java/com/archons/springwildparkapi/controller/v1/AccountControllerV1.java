package com.archons.springwildparkapi.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.requests.UpdateAccountRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.service.AccountService;
import com.archons.springwildparkapi.service.JwtService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountControllerV1 {
    /*
     * This controller class handles all account related requests
     * 
     * Endpoints:
     * GET /api/v1/accounts/
     * GET /api/v1/accounts/{id}
     * PUT /api/v1/accounts/{id}
     * GET /api/v1/accounts/{id}/vehicles
     * GET /api/v1/accounts/{id}/organizations
     * GET /api/v1/accounts/{id}/bookings
     * GET /api/v1/accounts/{id}/payments
     * 
     */

    private final AccountService accountService;

    @Autowired
    public AccountControllerV1(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(
            @RequestHeader(name = "Authorization") String authorization) {
        /* Retrieve list of all accounts */
        try {
            return ResponseEntity.ok(accountService.getAllAccounts(authorization));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int accountId) {
        /* Retrieves the account with the specified id */
        try {
            return ResponseEntity.ok(accountService.getAccountById(authorization, accountId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateAccountRequest request,
            @PathVariable int accountId) {
        /* Updates an account */
        try {
            return ResponseEntity.ok(accountService.updateAccount(authorization, request, accountId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/{accountId}/vehicles")
    public ResponseEntity<?> getAllAccountVehicles(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountVehicles(authorization, accountId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/{accountId}/organizations")
    public ResponseEntity<?> getAccountOrganizations(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountOrganizations(authorization, accountId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/{accountId}/bookings")
    public ResponseEntity<?> getAccountBookings(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountBookings(authorization, accountId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/{accountId}/payments")
    public ResponseEntity<?> getAccountPayments(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountPayments(authorization, accountId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }
}