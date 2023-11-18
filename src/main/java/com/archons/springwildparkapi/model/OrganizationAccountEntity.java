package com.archons.springwildparkapi.model;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblorganizationmember")
public class OrganizationAccountEntity {
    @EmbeddedId
    private OrganizationAccountId organizationAccountId;

    @ManyToOne
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    private OrganizationRole organizationRole;

    public OrganizationAccountEntity() {
    }

    public OrganizationAccountEntity(OrganizationAccountId organizationAccountId, OrganizationEntity organization,
            AccountEntity account, OrganizationRole organizationRole) {
        this.organizationAccountId = organizationAccountId;
        this.organization = organization;
        this.account = account;
        this.organizationRole = organizationRole;
    }

    public OrganizationAccountId getOrganizationMemberId() {
        return organizationAccountId;
    }

    public void setOrganizationMemberId(OrganizationAccountId organizationAccountId) {
        this.organizationAccountId = organizationAccountId;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public OrganizationRole getOrganizationRole() {
        return organizationRole;
    }

    public void setOrganizationRole(OrganizationRole organizationRole) {
        this.organizationRole = organizationRole;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((organizationAccountId == null) ? 0 : organizationAccountId.hashCode());
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((organizationRole == null) ? 0 : organizationRole.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrganizationAccountEntity other = (OrganizationAccountEntity) obj;
        if (organizationAccountId == null) {
            if (other.organizationAccountId != null)
                return false;
        } else if (!organizationAccountId.equals(other.organizationAccountId))
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (organizationRole != other.organizationRole)
            return false;
        return true;
    }
}

class OrganizationAccountId implements Serializable {
    private int accountId;
    private int organizationId;

    public OrganizationAccountId() {
    }

    public OrganizationAccountId(int accountId, int organizationId) {
        this.accountId = accountId;
        this.organizationId = organizationId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + accountId;
        result = prime * result + organizationId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrganizationAccountId other = (OrganizationAccountId) obj;
        if (accountId != other.accountId)
            return false;
        if (organizationId != other.organizationId)
            return false;
        return true;
    }

}
