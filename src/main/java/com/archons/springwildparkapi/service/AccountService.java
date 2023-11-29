package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AccountOrganizationsResponse;
import com.archons.springwildparkapi.dto.UpdateAccountRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AccountEntity getAccountFromToken(String authorization) throws AccountNotFoundException {
        String token = jwtService.extractBearerToken(authorization);
        String email = jwtService.extractUsername(token);
        Optional<AccountEntity> existingAccount = accountRepository.findByEmail(email);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get();
    }

    public List<AccountEntity> getAllAccounts(String authorization)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        // Checks if the requester is an admin
        if (requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Iterable<AccountEntity> iterable = accountRepository.findAll();
        List<AccountEntity> userList = new ArrayList<>();

        for (AccountEntity a : iterable) {
            if (a.getEnabled())
                userList.add(a);
        }

        return userList;
    }

    public AccountEntity getAccountById(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<AccountEntity> existingAccount = accountRepository.findById(accountId);

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        return existingAccount.get();
    }

    public AccountEntity updateAccount(String authorization, UpdateAccountRequest request, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity updatedAccount = request.getUpdatedAccount();

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity account = getAccountById(authorization, accountId);

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

        return accountRepository.save(account);
    }

    public boolean deleteAccount(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity account = getAccountById(authorization, accountId);
        account.setEnabled(false);
        accountRepository.save(account);
        return true;
    }

    public List<VehicleEntity> getAccountVehicles(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return getAccountById(authorization, accountId).getVehicles();
    }

    public AccountOrganizationsResponse getAccountOrganizations(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity account = getAccountById(authorization, accountId);

        AccountOrganizationsResponse response = new AccountOrganizationsResponse();
        response.setOwnedOrganizations(account.getOwnedOrganizations());
        response.setAdminOrganizations(account.getAdminOrganizations());
        response.setMemberOrganizations(account.getMemberOrganizations());

        return response;
    }

    public List<BookingEntity> getAccountBookings(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return getAccountById(authorization, accountId).getBookings();
    }

    public List<PaymentEntity> getAccountPayments(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        if (requester.getId() != accountId && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return getAccountById(authorization, accountId).getPayments();
    }
}
