package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.OrganizationRepository;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationAccountService organizationAccountService;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository,
            OrganizationAccountService organizationAccountService) {
        this.organizationRepository = organizationRepository;
        this.organizationAccountService = organizationAccountService;
    }

    public Optional<OrganizationEntity> addOrganization(AccountEntity requester, OrganizationEntity newOrganization)
            throws InsufficientPrivilegesException {
        if (requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(organizationRepository.save(newOrganization));
    }

    public List<OrganizationEntity> getAllOrganizations() {
        Iterable<OrganizationEntity> iterable = organizationRepository.findAll();
        List<OrganizationEntity> organizationList = new ArrayList<>();
        iterable.forEach(organizationList::add);
        return organizationList;
    }

    public Optional<OrganizationEntity> getOrganizationById(AccountEntity requester, int organizationId)
            throws OrganizationNotFoundException, InsufficientPrivilegesException {
        Optional<OrganizationEntity> existingOrganization = organizationRepository.findById(organizationId);

        if (existingOrganization.isPresent()) {
            OrganizationEntity organization = existingOrganization.get();

            if (!organizationAccountService.isOrganizationInAccount(requester, organization) &&
                    requester.getRole() != Role.ADMIN) {
                throw new InsufficientPrivilegesException();
            }

            return Optional.of(organization);
        } else {
            throw new OrganizationNotFoundException();
        }
    }

    public Optional<OrganizationEntity> updateOrganization(AccountEntity requester,
            OrganizationEntity updatedOrganization, int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException {

        if (!requester.equals(updatedOrganization.getOrganizationAccounts()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<OrganizationEntity> existingOrganization = getOrganizationById(requester, organizationId);

        if (existingOrganization.isPresent()) {
            throw new OrganizationNotFoundException();
        }

        return Optional.of(organizationRepository.save(updatedOrganization));

    }

    public void deleteOrganization(AccountEntity requester, int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException {
        Optional<OrganizationEntity> existingOrganization = organizationRepository.findById(organizationId);

        if (existingOrganization.isPresent()) {
            OrganizationEntity organization = existingOrganization.get();
            if (organization.getOrganizationAccounts() != requester && requester.getRole() != Role.ADMIN) {
                throw new InsufficientPrivilegesException();
            }

            organizationRepository.delete(organization);
        } else {
            throw new OrganizationNotFoundException();
        }

    }
}
