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
    private OrganizationMemberId organizationMemberId;

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
}

class OrganizationMemberId implements Serializable {
    private int accountId;
    private int organizationId;

    public OrganizationMemberId() {
    }

    public OrganizationMemberId(int accountId, int organizationId) {
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
        OrganizationMemberId other = (OrganizationMemberId) obj;
        if (accountId != other.accountId)
            return false;
        if (organizationId != other.organizationId)
            return false;
        return true;
    }

}
