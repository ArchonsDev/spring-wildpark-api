package com.archons.springwildparkapi.dto.reesponses;

import java.util.List;

import com.archons.springwildparkapi.model.OrganizationEntity;

public class AccountOrganizationsResponse {
    private List<OrganizationEntity> ownedOrganizations;

    private List<OrganizationEntity> adminOrganizations;

    private List<OrganizationEntity> memberOrganizations;

    public AccountOrganizationsResponse(List<OrganizationEntity> ownedOrganizations,
            List<OrganizationEntity> adminOrganizations, List<OrganizationEntity> memberOrganizations) {
        this.ownedOrganizations = ownedOrganizations;
        this.adminOrganizations = adminOrganizations;
        this.memberOrganizations = memberOrganizations;
    }

    public AccountOrganizationsResponse() {
    }

    public List<OrganizationEntity> getOwnedOrganizations() {
        return ownedOrganizations;
    }

    public void setOwnedOrganizations(List<OrganizationEntity> ownedOrganizations) {
        this.ownedOrganizations = ownedOrganizations;
    }

    public List<OrganizationEntity> getAdminOrganizations() {
        return adminOrganizations;
    }

    public void setAdminOrganizations(List<OrganizationEntity> adminOrganizations) {
        this.adminOrganizations = adminOrganizations;
    }

    public List<OrganizationEntity> getMemberOrganizations() {
        return memberOrganizations;
    }

    public void setMemberOrganizations(List<OrganizationEntity> memberOrganizations) {
        this.memberOrganizations = memberOrganizations;
    }
}
