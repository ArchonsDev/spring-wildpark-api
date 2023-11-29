package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.OrganizationEntity;

public class UpdateOrganizationRequest {
    private OrganizationEntity updatedOrganization;

    public UpdateOrganizationRequest() {
    }

    public UpdateOrganizationRequest(OrganizationEntity updatedOrganization) {
        this.updatedOrganization = updatedOrganization;
    }

    public OrganizationEntity getUpdatedOrganization() {
        return updatedOrganization;
    }

    public void setUpdatedOrganization(OrganizationEntity updatedOrganization) {
        this.updatedOrganization = updatedOrganization;
    }
}
