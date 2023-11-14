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
import com.archons.springwildparkapi.service.VehicleService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountControllerV1 {
    private final AccountService accountService;
    private final VehicleService vehicleService;

    @Autowired
    public AccountControllerV1(AccountService accountService, VehicleService vehicleService) {
        this.accountService = accountService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountEntity>> getAllAccounts(@RequestBody AccountEntity requester) {
        /* Retrieve list of all accounts */
        List<AccountEntity> accountList;

        try {
            accountList = accountService.getAllAccounts(requester);
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(accountList);
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

    @PutMapping("/")
    public ResponseEntity<Optional<AccountEntity>> updateAccount(
            @RequestBody AccountUpdateRequest accountUpdateRequest) {
        /* Updates an account */
        Optional<AccountEntity> account;

        try {
            account = accountService.updateAccount(accountUpdateRequest);
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }

        if (!account.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(account);
    }

    @GetMapping("{accountId}/vehicles")
    public ResponseEntity<List<VehicleEntity>> getAllAccountVehicles(@RequestParam int accountId,
            @RequestBody AccountEntity requester) {
        Optional<AccountEntity> existingAccount = accountService.getAccountById(accountId);

        if (!existingAccount.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<VehicleEntity> vehicleList;

        try {
            vehicleList = vehicleService.getAllAccountVehicles(existingAccount.get(), requester);
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(vehicleList);
    }

    @GetMapping("{accountId}/vehicles/{vehicleId}")
    public ResponseEntity<Optional<VehicleEntity>> getAccountVehicle(@RequestParam int accountId,
            @RequestParam int vehicleId,
            @RequestBody AccountEntity requester) {
        Optional<AccountEntity> existingAccount = accountService.getAccountById(accountId);

        if (!existingAccount.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<VehicleEntity> existingVehicle;

        try {
            existingVehicle = vehicleService.getAccountVehicle(existingAccount.get(), requester,
                    vehicleId);
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(existingVehicle);
    }
}