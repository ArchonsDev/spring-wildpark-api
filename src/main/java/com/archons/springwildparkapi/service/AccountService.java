package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AccountUpdateRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivillegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.OrganizationAccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountEntity> getAllAccounts(AccountEntity requester) throws InsufficientPrivillegesException {
        // Checks if the requester is an admin
        if (requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Iterable<AccountEntity> iterable = accountRepository.findAll();
        List<AccountEntity> userList = new ArrayList<>();
        iterable.forEach(userList::add);
        return userList;
    }

    public Optional<AccountEntity> getAccountById(AccountEntity requester, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = accountRepository.findById(accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return Optional.of(existingAccount.get());
    }

    public Optional<AccountEntity> updateAccount(AccountUpdateRequest accountUpdateRequest, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        AccountEntity requester = accountUpdateRequest.getRequester();
        AccountEntity updatedAccount = accountUpdateRequest.getUpdatedAccount();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requester, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return Optional.of(accountRepository.save(updatedAccount));
    }

    public boolean deleteAccount(AccountEntity requester, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requester, accountId);

        if (existingAccount.isPresent()) {
            accountRepository.delete(existingAccount.get());
            return true;
        }

        return false;
    }

    public List<VehicleEntity> getAccountVehicles(AccountEntity requester, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requester, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getVehicles();
    }

    public List<OrganizationEntity> getAccountOrganizations(AccountEntity requester, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requester, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getOrganizationAccounts().stream()
                .map(OrganizationAccountEntity::getOrganization)
                .collect(Collectors.toList());
    }

    public List<BookingEntity> getAccountBookings(AccountEntity requester, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requester, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getBookings();
    }

    public List<PaymentEntity> getAccountPayments(AccountEntity requester, int accountId)
            throws InsufficientPrivillegesException, AccountNotFoundException {
        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requester, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getPayments();
    }
}
