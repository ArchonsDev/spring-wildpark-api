package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AccountUpdateRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AccountEntity> getAllAccounts(int requesterId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        // Checks if the requester is an admin
        if (requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Iterable<AccountEntity> iterable = accountRepository.findAll();
        List<AccountEntity> userList = new ArrayList<>();
        iterable.forEach(userList::add);
        return userList;
    }

    public Optional<AccountEntity> getAccountById(int requesterId, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = accountRepository.findById(accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return Optional.of(existingAccount.get());
    }

    public Optional<AccountEntity> updateAccount(AccountUpdateRequest accountUpdateRequest, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(accountUpdateRequest.getRequesterId());

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();
        AccountEntity updatedAccount = accountUpdateRequest.getUpdatedAccount();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(accountUpdateRequest.getRequesterId(), accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity account = existingAccount.get();

        // Updateable fields:

        if (updatedAccount.getPassword() != null) {
            account.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
        }

        if (updatedAccount.getFirstname() != null) {
            account.setFirstname(updatedAccount.getFirstname());
        }

        if (updatedAccount.getLastname() != null) {
            account.setLastname(updatedAccount.getLastname());
        }

        if (updatedAccount.getBirthdate() != null) {
            account.setBirthdate(updatedAccount.getBirthdate());
        }

        if (updatedAccount.getContactNo() != null) {
            account.setContactNo(updatedAccount.getContactNo());
        }

        if (updatedAccount.getGender() != null) {
            account.setGender(updatedAccount.getGender());
        }

        if (updatedAccount.getStreet() != null) {
            account.setStreet(updatedAccount.getStreet());
        }

        if (updatedAccount.getMunicipality() != null) {
            account.setMunicipality(updatedAccount.getMunicipality());
        }

        if (updatedAccount.getProvince() != null) {
            account.setProvince(updatedAccount.getProvince());
        }

        if (updatedAccount.getCountry() != null) {
            account.setCountry(updatedAccount.getCountry());
        }

        if (updatedAccount.getZipCode() != 0) {
            account.setZipCode(updatedAccount.getZipCode());
        }

        if (updatedAccount.getRole() != null) {
            account.setRole(updatedAccount.getRole());
        }

        return Optional.of(accountRepository.save(account));
    }

    public boolean deleteAccount(int requesterId, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requesterId, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity account = existingAccount.get();
        account.setEnabled(false);
        accountRepository.save(account);
        return true;
    }

    public List<VehicleEntity> getAccountVehicles(int requesterId, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requesterId, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getVehicles();
    }

    public List<OrganizationEntity> getAccountOrganizations(int requesterId, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requesterId, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getOrganizationAccounts().stream()
                .map(OrganizationAccountEntity::getOrganization)
                .collect(Collectors.toList());
    }

    public List<BookingEntity> getAccountBookings(int requesterId, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requesterId, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getBookings();
    }

    public List<PaymentEntity> getAccountPayments(int requesterId, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountRepository.findById(requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = getAccountById(requesterId, accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get().getPayments();
    }
}
