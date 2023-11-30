package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AccountOrganizationsResponse;
import com.archons.springwildparkapi.dto.AuthenticationRequest;
import com.archons.springwildparkapi.dto.AuthenticationResponse;
import com.archons.springwildparkapi.dto.RegisterAccountRequest;
import com.archons.springwildparkapi.dto.UpdateAccountRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AccountService extends BaseService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        super(passwordEncoder);
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AccountEntity getAccountFromToken(String authorization) throws AccountNotFoundException {
        String email = jwtService.extractUsername(jwtService.extractBearerToken(authorization));

        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException());
    }

    public AuthenticationResponse register(RegisterAccountRequest request) throws DuplicateEntityException {
        // Creates a new user and returns a JWT Token
        Optional<AccountEntity> existingAccount = accountRepository.findByEmail(request.getEmail());

        if (existingAccount.isPresent()) {
            throw new DuplicateEntityException();
        }

        AccountEntity account = new AccountEntity();

        // Set fields
        account.setFirstname(request.getFirstname());
        account.setLastname(request.getLastname());
        account.setEmail(request.getEmail());
        account.setPassword(encodePassword(request.getPassword()));
        account.setRole(Role.USER);
        account.setEnabled(true);

        // Save user
        account = accountRepository.save(account);

        // Create JWT Token
        String jwtToken = jwtService.generateToken(account);

        account.setPassword("REDACTED");
        return new AuthenticationResponse(jwtToken, account);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AccountNotFoundException {
        // Find user from DB
        Optional<AccountEntity> existingAccount = accountRepository.findByEmail(request.getEmail());

        if (!existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        // Authenticate a user from the provided email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        AccountEntity account = existingAccount.get();

        // Generate JWT Token
        String jtwToken = jwtService.generateToken(account);

        account.setPassword("REDACTED");
        return new AuthenticationResponse(jtwToken, account);
    }

    public List<AccountEntity> getAllAccounts(String authorization)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);

        // Checks if the requester is an admin
        if (!isAccountAdmin(requester)) {
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
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException());

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return account;
    }

    public AccountEntity updateAccount(String authorization, UpdateAccountRequest request, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        AccountEntity updatedAccount = request.getUpdatedAccount();

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        // Updateable fields:
        if (updatedAccount.getPassword() != null) {
            account.setPassword(encodePassword(updatedAccount.getPassword()));
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

    public void deleteAccount(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        account.setEnabled(false);
        accountRepository.save(account);
    }

    public List<VehicleEntity> getAccountVehicles(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return account.getVehicles();
    }

    public AccountOrganizationsResponse getAccountOrganizations(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        AccountOrganizationsResponse response = new AccountOrganizationsResponse();
        response.setOwnedOrganizations(account.getOwnedOrganizations());
        response.setAdminOrganizations(account.getAdminOrganizations());
        response.setMemberOrganizations(account.getMemberOrganizations());

        return response;
    }

    public List<BookingEntity> getAccountBookings(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return account.getBookings();
    }

    public List<PaymentEntity> getAccountPayments(String authorization, int accountId)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return account.getPayments();
    }
}
