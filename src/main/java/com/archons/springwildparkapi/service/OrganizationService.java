package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.dto.reesponses.OrganizationMemberResponse;
import com.archons.springwildparkapi.dto.requests.AddOrganizationRequest;
import com.archons.springwildparkapi.dto.requests.UpdateOrganizationRequest;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.repository.OrganizationRepository;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService extends BaseService {
    private final OrganizationRepository organizationRepository;
    private final AccountService accountService;

    public OrganizationService(OrganizationRepository organizationRepository, AccountService accountService) {
        this.organizationRepository = organizationRepository;
        this.accountService = accountService;
    }

    @Transactional
    public OrganizationEntity addOrganization(String authorization, AddOrganizationRequest request) throws Exception {
        AccountEntity requester = accountService.getAccountFromToken(authorization);

        OrganizationEntity newOrganization = request.getNewOrganization();
        Optional<OrganizationEntity> existingOrganization = organizationRepository
                .findByName(newOrganization.getName());

        if (existingOrganization.isPresent()) {
            throw new DuplicateEntityException();
        }

        if (newOrganization.getName() == null ||
                newOrganization.getLatitude() == 0 ||
                newOrganization.getLongitude() == 0 ||
                newOrganization.getPaymentStrategy() == null ||
                newOrganization.getType() == null) {
            throw new IncompleteRequestException();
        }

        newOrganization.setOwner(requester);
        return organizationRepository.save(newOrganization);
    }

    public List<OrganizationEntity> getAllOrganizations() {
        Iterable<OrganizationEntity> iterable = organizationRepository.findAll();
        List<OrganizationEntity> organizationList = new ArrayList<>();

        for (OrganizationEntity org : iterable) {
            if (!org.getDeleted()) {
                organizationList.add(org);
            }
        }

        return organizationList;
    }

    public OrganizationEntity getOrganizationById(int organizationId)
            throws OrganizationNotFoundException {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
    }

    public OrganizationEntity updateOrganization(String authorization, UpdateOrganizationRequest request,
            int organizationId) throws Exception {
        // Wrapper exception
        try {
            // Retrieve entities
            AccountEntity requester = accountService.getAccountFromToken(authorization);
            OrganizationEntity organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new OrganizationNotFoundException());
            // Check permissions
            if (!isOrganizationAdmin(organization, requester) && !isAccountAdmin(requester)) {
                throw new InsufficientPrivilegesException();
            }
            // Update fields
            if (request.getName() != null) {
                Optional<OrganizationEntity> existingOrganization = organizationRepository
                        .findByName(request.getName());
                // Check uniqueness
                if (existingOrganization.isPresent()) {
                    throw new DuplicateEntityException();
                }

                organization.setName(request.getName());
            }
            if (request.getLatitude() != 0) {
                organization.setLatitude(request.getLatitude());
            }
            if (request.getLongitude() != 0) {
                organization.setLongitude(request.getLongitude());
            }
            if (request.getPaymentStrategy() != null) {
                organization.setPaymentStrategy(request.getPaymentStrategy());
            }
            if (request.getOrganizationType() != null) {
                organization.setType(request.getOrganizationType());
            }
            if (request.getOwnerId() != 0) {
                if (!isOrganizationOwner(organization, requester)) {
                    throw new InsufficientPrivilegesException();
                }

                AccountEntity account = accountService.getAccountById(authorization, request.getOwnerId());
                organization.setOwner(account);
            }
            if (request.isDeleted() != organization.getDeleted()) {
                organization.setDeleted(request.isDeleted());
            }

            return organizationRepository.save(organization);
        } catch (DuplicateEntityException ex) {
            throw new IncompleteRequestException();
        }
    }

    public void deleteOrganization(String authorization, int organizationId) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
        // Check permissions
        if (!isOrganizationOwner(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Set deleted
        organization.setDeleted(true);
        organizationRepository.save(organization);
    }

    public OrganizationMemberResponse getOrganizationAccounts(String authorization, int organizationId)
            throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Build response
        OrganizationMemberResponse response = new OrganizationMemberResponse();
        response.setOwner(organization.getOwner());
        response.setAdmins(organization.getAdmins());
        response.setMembers(organization.getMembers());

        return response;
    }

    @Transactional
    public OrganizationMemberResponse addOrganizationMember(String authorization, int accountId, int organizationId)
            throws Exception {
        // Retreive entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        AccountEntity account = accountService.getAccountById(authorization, accountId);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Check existence
        if (isOrganizationMember(organization, account)) {
            throw new DuplicateEntityException();
        }
        // Update organization members
        List<AccountEntity> members = organization.getMembers();
        members.add(account);
        organization.setMembers(members);
        organization = organizationRepository.save(organization);
        // Build response
        OrganizationMemberResponse response = new OrganizationMemberResponse();
        response.setOwner(organization.getOwner());
        response.setAdmins(organization.getAdmins());
        response.setMembers(organization.getMembers());

        return response;
    }

    @Transactional
    public OrganizationMemberResponse addOrganizationAdmin(String authorization, int accountId, int organizationId)
            throws Exception {
        // Retreive entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        AccountEntity account = accountService.getAccountById(authorization, accountId);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Check existence
        if (!isOrganizationMember(organization, account)) {
            throw new AccountNotFoundException();
        }
        // Update organization members
        List<AccountEntity> members = organization.getMembers();
        members.remove(account);
        organization.setMembers(members);
        // Update organizationa dmins
        List<AccountEntity> admins = organization.getAdmins();
        admins.add(account);
        organization.setAdmins(members);

        organization = organizationRepository.save(organization);
        // Build response
        OrganizationMemberResponse response = new OrganizationMemberResponse();
        response.setOwner(organization.getOwner());
        response.setAdmins(organization.getAdmins());
        response.setMembers(organization.getMembers());

        return response;
    }

    public void kickMember(String authorization, int accountId, int organizationId) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        AccountEntity account = accountService.getAccountById(authorization, accountId);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Check presence
        if (!isOrganizationMember(organization, account)) {
            throw new AccountNotFoundException();
        }
        // Update members
        List<AccountEntity> members = organization.getMembers();
        members.remove(account);
        organization.setMembers(members);
        organizationRepository.save(organization);
    }

    public void demote(String authorization, int accountId, int organizationId) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        AccountEntity account = accountService.getAccountById(authorization, accountId);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Check presence
        if (!isOrganizationAdmin(organization, account)) {
            throw new AccountNotFoundException();
        }
        // Update members
        List<AccountEntity> admins = organization.getAdmins();
        admins.remove(account);
        organization.setAdmins(admins);
        List<AccountEntity> members = organization.getMembers();
        members.add(account);
        organization.setMembers(members);

        organizationRepository.save(organization);
    }
}
