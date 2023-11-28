package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.OrganizationEntity;

public class OrganizationUpdateRequest {
    private int requesterId;
    private OrganizationEntity updatedOrganization;

    public OrganizationUpdateRequest() {
    }

    public OrganizationUpdateRequest(int requesterId, OrganizationEntity updatedOrganization) {
        this.requesterId = requesterId;
        this.updatedOrganization = updatedOrganization;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public OrganizationEntity getUpdatedOrganization() {
        return updatedOrganization;
    }

    public void setUpdatedOrganization(OrganizationEntity updatedOrganization) {
        this.updatedOrganization = updatedOrganization;
    }
}
