package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.repository.OrganizationRepository;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<OrganizationEntity> getAllOrganizations() {
        Iterable<OrganizationEntity> iterable = organizationRepository.findAll();
        List<OrganizationEntity> organizationList = new ArrayList<>();
        iterable.forEach(organizationList::add);
        return organizationList;
    }

    public Optional<OrganizationEntity> getOrganizationById(int organizationId) {
        return organizationRepository.findById(organizationId);
    }

    public Optional<OrganizationEntity> updateOrganization(OrganizationEntity updatedOrganization) {
        Optional<OrganizationEntity> existingOrganization = organizationRepository
                .findById(updatedOrganization.getId());

        if (existingOrganization.isPresent()) {
            return Optional.of(organizationRepository.save(updatedOrganization));
        }

        return Optional.ofNullable(null);
    }

    public boolean deleteOrganization(int organizationId) {
        Optional<OrganizationEntity> existingOrganization = organizationRepository.findById(organizationId);

        if (existingOrganization.isPresent()) {
            organizationRepository.delete(existingOrganization.get());
            return true;
        }

        return false;
    }
}
