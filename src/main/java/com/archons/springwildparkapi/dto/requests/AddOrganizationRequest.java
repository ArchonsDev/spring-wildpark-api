package com.archons.springwildparkapi.dto.requests;

import com.archons.springwildparkapi.model.OrganizationEntity;

public class AddOrganizationRequest {
    private OrganizationEntity newOrganization;

    public AddOrganizationRequest(OrganizationEntity newOrganization) {
        this.newOrganization = newOrganization;
    }

    public AddOrganizationRequest() {
    }

    public OrganizationEntity getNewOrganization() {
        return newOrganization;
    }

    public void setNewOrganization(OrganizationEntity newOrganization) {
        this.newOrganization = newOrganization;
    }
}
