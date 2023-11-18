package com.archons.springwildparkapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationAccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.OrganizationRole;
import com.archons.springwildparkapi.repository.OrganizationAccountRepository;

@Service
public class OrganizationAccountService {
    private final OrganizationAccountRepository organizationAccountRepository;

    @Autowired
    public OrganizationAccountService(OrganizationAccountRepository organizationAccountRepository) {
        this.organizationAccountRepository = organizationAccountRepository;
    }

    public boolean isOrganizationInAccount(AccountEntity account, OrganizationEntity organization) {
        List<OrganizationAccountEntity> associations = organizationAccountRepository
                .findByAccountIdAndOrganizationId(account.getId(), organization.getId());

        return !associations.isEmpty();
    }

    public OrganizationRole getOrganizationRole(AccountEntity account, OrganizationEntity organization) {
        return organizationAccountRepository.findOrganizationRoleByAccountIdAndOrganizationId(account.getId(),
                organization.getId());
    }
}
