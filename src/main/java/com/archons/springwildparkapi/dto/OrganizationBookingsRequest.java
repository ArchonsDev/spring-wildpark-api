package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;

public class OrganizationBookingsRequest {
    private AccountEntity requester;
    private OrganizationEntity organization;

    public OrganizationBookingsRequest() {
    }

    public OrganizationBookingsRequest(AccountEntity requester, OrganizationEntity organization) {
        this.requester = requester;
        this.organization = organization;
    }

    public AccountEntity getRequester() {
        return requester;
    }

    public void setRequester(AccountEntity requester) {
        this.requester = requester;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requester == null) ? 0 : requester.hashCode());
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
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
        OrganizationBookingsRequest other = (OrganizationBookingsRequest) obj;
        if (requester == null) {
            if (other.requester != null)
                return false;
        } else if (!requester.equals(other.requester))
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        return true;
    }
}
