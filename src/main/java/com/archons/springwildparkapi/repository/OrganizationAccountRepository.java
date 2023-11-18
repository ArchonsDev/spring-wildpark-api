package com.archons.springwildparkapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.OrganizationAccountEntity;
import com.archons.springwildparkapi.model.OrganizationRole;

@Repository
public interface OrganizationAccountRepository extends CrudRepository<OrganizationAccountEntity, Integer> {
    List<OrganizationAccountEntity> findByIdAccountIdAndIdOrganizationId(int accountId, int organizationId);

    @Query("SELECT o.organizationRole FROM OrganizationAccountEntity o WHERE o.organizationAccountId.accountId = :accountId AND o.organizationAccountId.organizationId = :organizationId")
    OrganizationRole findOrganizationRoleByAccountIdAndOrganizationId(
            @Param("accountId") int accountId,
            @Param("organizationId") int organizationId);
}
