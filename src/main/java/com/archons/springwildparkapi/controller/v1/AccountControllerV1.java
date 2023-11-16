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
import com.archons.springwildparkapi.exceptions.InsufficientPrivillegesException;
import com.archons.springwildparkapi.model.AccountEntity;
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
     * TODO: GET /api/v1/accounts/{id}/organizations
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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AccountEntity>> getAccountById(@RequestParam int id) {
        /* Retrieves the account with the specified id */
        Optional<AccountEntity> account = accountService.getAccountById(id);

        if (!account.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<AccountEntity>> updateAccount(
            @RequestBody AccountUpdateRequest accountUpdateRequest,
            @RequestParam int accountId) {
        /* Updates an account */
        try {
            Optional<AccountEntity> account = accountService.updateAccount(accountUpdateRequest, accountId);

            if (!account.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(account);
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{accountId}/vehicles")
    public ResponseEntity<List<VehicleEntity>> getAllAccountVehicles(@RequestParam int accountId,
            @RequestBody AccountEntity requester) {
        Optional<AccountEntity> existingAccount = accountService.getAccountById(accountId);

        if (!existingAccount.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<VehicleEntity> vehicleList = existingAccount.get().getVehicles();

        return ResponseEntity.ok(vehicleList);
    }
}