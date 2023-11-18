package com.archons.springwildparkapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.OrganizationAccountEntity;

@Repository
public interface OrganizationAccountRepository extends CrudRepository<OrganizationAccountEntity, Integer> {
    List<OrganizationAccountEntity> findByIdAccountIdAndIdOrganizationId(int accountId, int organizationId);
}
