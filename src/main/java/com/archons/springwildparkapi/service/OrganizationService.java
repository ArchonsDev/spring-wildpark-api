package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.dto.AddOrganizationRequest;
import com.archons.springwildparkapi.dto.UpdateOrganizationRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.OrganizationRepository;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final AccountService accountService;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, AccountService accountService) {
        this.organizationRepository = organizationRepository;
        this.accountService = accountService;
    }

    public boolean isOrganizationOwner(OrganizationEntity organization, AccountEntity account) {
        if (organization.getOwner().equals(account)) {
            return true;
        }

        return false;
    }

    public boolean isOrganizationAdmin(OrganizationEntity organization, AccountEntity account) {
        List<AccountEntity> organizationAdmins = organization.getAdmins();

        for (AccountEntity a : organizationAdmins) {
            if (a.equals(account)) {
                return true;
            }
        }

        return false;
    }

    public boolean isOrganizationMember(OrganizationEntity organization, AccountEntity account) {
        List<AccountEntity> organizationMembers = organization.getMembers();

        for (AccountEntity a : organizationMembers) {
            if (a.equals(account)) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public OrganizationEntity addOrganization(String authorization, AddOrganizationRequest request)
            throws InsufficientPrivilegesException, AccountNotFoundException, DuplicateEntityException,
            IncompleteRequestException {
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

    public OrganizationEntity getOrganizationById(String authorization, int organizationId)
            throws OrganizationNotFoundException, InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<OrganizationEntity> existingOrganization = organizationRepository.findById(organizationId);

        if (!existingOrganization.isPresent()) {
            throw new OrganizationNotFoundException();
        }

        OrganizationEntity organization = existingOrganization.get();

        if (!isOrganizationMember(organization, requester) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return organization;
    }

    public OrganizationEntity updateOrganization(String authorization, UpdateOrganizationRequest request,
            int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException, AccountNotFoundException,
            IncompleteRequestException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        OrganizationEntity updatedOrganization = request.getUpdatedOrganization();

        if (updatedOrganization.getId() == 0) {
            throw new IncompleteRequestException();
        }

        OrganizationEntity organization = getOrganizationById(authorization, organizationId);

        if ((!isOrganizationAdmin(organization, requester) || !isOrganizationOwner(organization, requester))
                && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        if (updatedOrganization.getName() != null) {
            organization.setName(updatedOrganization.getName());
        }

        if (updatedOrganization.getLatitude() != 0) {
            organization.setLatitude(updatedOrganization.getLatitude());
        }

        if (updatedOrganization.getLongitude() != 0) {
            organization.setLongitude(updatedOrganization.getLongitude());
        }

        if (updatedOrganization.getPaymentStrategy() != null) {
            organization.setPaymentStrategy(updatedOrganization.getPaymentStrategy());
        }

        if (updatedOrganization.getType() != null) {
            organization.setType(updatedOrganization.getType());
        }

        if (updatedOrganization.getDeleted() != organization.getDeleted()) {
            organization.setDeleted(updatedOrganization.getDeleted());
        }

        return organizationRepository.save(organization);
    }

    public void deleteOrganization(String authorization, int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<OrganizationEntity> existingOrganization = organizationRepository.findById(organizationId);

        if (!existingOrganization.isPresent()) {
            throw new OrganizationNotFoundException();
        }

        OrganizationEntity organization = existingOrganization.get();
        if ((!isOrganizationAdmin(organization, requester) || !isOrganizationOwner(organization, requester))
                && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        organization.setDeleted(true);
        organizationRepository.save(organization);
    }
}
