package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.reesponses.AccountOrganizationsResponse;
import com.archons.springwildparkapi.dto.reesponses.AuthenticationResponse;
import com.archons.springwildparkapi.dto.requests.AuthenticationRequest;
import com.archons.springwildparkapi.dto.requests.RegisterAccountRequest;
import com.archons.springwildparkapi.dto.requests.UpdateAccountRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AccountService extends BaseService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        super(passwordEncoder);
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AccountEntity getAccountFromToken(String authorization) throws Exception {
        // Get email from token
        String email = jwtService.extractUsername(jwtService.extractBearerToken(authorization));

        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException());
    }

    public AuthenticationResponse register(RegisterAccountRequest request) throws Exception {
        // Creates a new user and returns a JWT Token
        Optional<AccountEntity> existingAccount = accountRepository.findByEmail(request.getEmail());
        // Check uniqueness
        if (existingAccount.isPresent()) {
            throw new DuplicateEntityException();
        }
        // Create instance
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
        // Mask password
        account.setPassword("REDACTED");
        return new AuthenticationResponse(jwtToken, account);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        // Authenticate a user from the provided email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // Retrieve account
        AccountEntity account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AccountNotFoundException());
        // Generate JWT Token
        String jtwToken = jwtService.generateToken(account);
        // Mask passowrd
        account.setPassword("REDACTED");
        return new AuthenticationResponse(jtwToken, account);
    }

    public List<AccountEntity> getAllAccounts(String authorization) throws Exception {
        // Retrieve requester account
        AccountEntity requester = getAccountFromToken(authorization);
        // Checks if the requester is an admin
        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Filter enabled accounts
        Iterable<AccountEntity> iterable = accountRepository.findAll();
        List<AccountEntity> userList = new ArrayList<>();
        for (AccountEntity a : iterable) {
            if (a.isEnabled())
                userList.add(a);
        }

        return userList;
    }

    public AccountEntity getAccountById(String authorization, int accountId) throws Exception {
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException());

        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return account;
    }

    public AccountEntity getAccountById(int accountId) throws Exception {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException());
    }

    public AccountEntity updateAccount(String authorization, UpdateAccountRequest request, int accountId)
            throws Exception {
        // Retrieve entities
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        // Check permissions
        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Updateable fields:
        if (request.getPassword() != null) {
            account.setPassword(encodePassword(request.getPassword()));
        }
        if (request.getFirstname() != null) {
            account.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null) {
            account.setLastname(request.getLastname());
        }
        if (request.getBirthdate() != null) {
            account.setBirthdate(parseDate(request.getBirthdate()));
        }
        if (request.getContactNo() != null) {
            account.setContactNo(request.getContactNo());
        }
        if (request.getGender() != null) {
            account.setGender(request.getGender());
        }
        if (request.getStreet() != null) {
            account.setStreet(request.getStreet());
        }
        if (request.getMunicipality() != null) {
            account.setMunicipality(request.getMunicipality());
        }
        if (request.getProvince() != null) {
            account.setProvince(request.getProvince());
        }
        if (request.getCountry() != null) {
            account.setCountry(request.getCountry());
        }
        if (request.getZipCode() != 0) {
            account.setZipCode(request.getZipCode());
        }

        return accountRepository.save(account);
    }

    public void deleteAccount(String authorization, int accountId) throws Exception {
        // Retreive entities
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        // Check permissions
        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Set disabled
        account.setEnabled(false);
        accountRepository.save(account);
    }

    public List<VehicleEntity> getAccountVehicles(String authorization, int accountId) throws Exception {
        // Retrieve entities
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        // Check permissions
        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        List<VehicleEntity> vehicles = new ArrayList<>();
        for (VehicleEntity v : account.getVehicles()) {
            if (!v.isDeleted()) {
                vehicles.add(v);
            }
        }

        return vehicles;
    }

    public AccountOrganizationsResponse getAccountOrganizations(String authorization, int accountId) throws Exception {
        // Retrieve entities
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        // Check permissions
        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Build response
        AccountOrganizationsResponse response = new AccountOrganizationsResponse();
        List<OrganizationEntity> ownedOrgs = new ArrayList<>();
        for (OrganizationEntity o : account.getOwnedOrganizations()) {
            if (!o.getDeleted()) {
                ownedOrgs.add(o);
            }
        }
        List<OrganizationEntity> adminOrgs = new ArrayList<>();
        for (OrganizationEntity o : account.getAdminOrganizations()) {
            if (!o.getDeleted()) {
                adminOrgs.add(o);
            }
        }
        List<OrganizationEntity> memberOrgs = new ArrayList<>();
        for (OrganizationEntity o : account.getMemberOrganizations()) {
            if (!o.getDeleted()) {
                memberOrgs.add(o);
            }
        }

        response.setOwnedOrganizations(ownedOrgs);
        response.setAdminOrganizations(adminOrgs);
        response.setMemberOrganizations(memberOrgs);

        return response;
    }

    public List<BookingEntity> getAccountBookings(String authorization, int accountId) throws Exception {
        // Retreive entities
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        // Check permissions
        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        List<BookingEntity> bookingList = new ArrayList<>();
        for (BookingEntity b : account.getBookings()) {
            if (!b.isDeleted()) {
                bookingList.add(b);
            }
        }

        return bookingList;
    }

    public List<PaymentEntity> getAccountPayments(String authorization, int accountId) throws Exception {
        // Retrieve entities
        AccountEntity requester = getAccountFromToken(authorization);
        AccountEntity account = getAccountById(authorization, accountId);
        // Check permissions
        if (!requester.equals(account) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return account.getPayments();
    }
}
