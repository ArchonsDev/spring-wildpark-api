package com.archons.springwildparkapi.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.AccountUpdateRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivillegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.service.AccountService;

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
     * TODO: GET /api/v1/accounts/{id}/bookings
     * TODO: GET /api/v1/accounts/{id}/payments
     * 
     */

    private final AccountService accountService;

    @Autowired
    public AccountControllerV1(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(@RequestBody AccountEntity requester) {
        /* Retrieve list of all accounts */
        try {
            return ResponseEntity.ok(accountService.getAllAccounts(requester));
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Optional<AccountEntity>> getAccountById(@RequestBody AccountEntity requester,
            @RequestParam int accountId) {
        /* Retrieves the account with the specified id */
        try {
            return ResponseEntity.ok(accountService.getAccountById(requester, accountId));
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<AccountEntity>> updateAccount(
            @RequestBody AccountUpdateRequest accountUpdateRequest,
            @RequestParam int accountId) {
        /* Updates an account */
        try {
            return ResponseEntity.ok(accountService.updateAccount(accountUpdateRequest, accountId));
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{accountId}/vehicles")
    public ResponseEntity<List<VehicleEntity>> getAllAccountVehicles(@RequestBody AccountEntity requester,
            @RequestParam int accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountVehicles(requester, accountId));
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{accountId}/organizations")
    public ResponseEntity<List<OrganizationEntity>> getAccountOrganizations(@RequestBody AccountEntity requester,
            @RequestParam int accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountOrganizations(requester, accountId));
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}