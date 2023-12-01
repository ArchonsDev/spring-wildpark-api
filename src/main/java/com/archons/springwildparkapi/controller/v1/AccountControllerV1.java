package com.archons.springwildparkapi.controller.v1;

import java.util.List;

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

    public AccountControllerV1(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(
            @RequestHeader(name = "Authorization") String authorization)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        /* Retrieve list of all accounts */
        return ResponseEntity.ok(accountService.getAllAccounts(authorization));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int accountId) throws InsufficientPrivilegesException, AccountNotFoundException {
        /* Retrieves the account with the specified id */
        return ResponseEntity.ok(accountService.getAccountById(authorization, accountId));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateAccountRequest request,
            @PathVariable int accountId) throws InsufficientPrivilegesException, AccountNotFoundException {
        /* Updates an account */
        return ResponseEntity.ok(accountService.updateAccount(authorization, request, accountId));
    }

    @GetMapping("/{accountId}/vehicles")
    public ResponseEntity<?> getAllAccountVehicles(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        return ResponseEntity.ok(accountService.getAccountVehicles(authorization, accountId));
    }

    @GetMapping("/{accountId}/organizations")
    public ResponseEntity<?> getAccountOrganizations(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        return ResponseEntity.ok(accountService.getAccountOrganizations(authorization, accountId));
    }

    @GetMapping("/{accountId}/bookings")
    public ResponseEntity<?> getAccountBookings(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        return ResponseEntity.ok(accountService.getAccountBookings(authorization, accountId));
    }

    @GetMapping("/{accountId}/payments")
    public ResponseEntity<?> getAccountPayments(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        return ResponseEntity.ok(accountService.getAccountPayments(authorization, accountId));
    }
}