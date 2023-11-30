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
import com.archons.springwildparkapi.repository.OrganizationRepository;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService extends BaseService {
    private final OrganizationRepository organizationRepository;
    private final AccountService accountService;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, AccountService accountService) {
        this.organizationRepository = organizationRepository;
        this.accountService = accountService;
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

    public OrganizationEntity getOrganizationById(int organizationId)
            throws OrganizationNotFoundException {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());
    }

    public OrganizationEntity updateOrganization(String authorization, UpdateOrganizationRequest request,
            int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException, AccountNotFoundException,
            IncompleteRequestException {
        try {
            AccountEntity requester = accountService.getAccountFromToken(authorization);
            OrganizationEntity organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new OrganizationNotFoundException());

            if ((!isOrganizationAdmin(organization, requester) || !isOrganizationOwner(organization, requester))
                    && !isAccountAdmin(requester)) {
                throw new InsufficientPrivilegesException();
            }

            if (request.getName() != null) {
                Optional<OrganizationEntity> existingOrganization = organizationRepository
                        .findByName(request.getName());

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

            if (request.isDeleted() != organization.getDeleted()) {
                organization.setDeleted(request.isDeleted());
            }

            return organizationRepository.save(organization);
        } catch (DuplicateEntityException ex) {
            throw new IncompleteRequestException();
        }
    }

    public void deleteOrganization(String authorization, int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException());

        if ((!isOrganizationAdmin(organization, requester) || !isOrganizationOwner(organization, requester))
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        organization.setDeleted(true);
        organizationRepository.save(organization);
    }
}
