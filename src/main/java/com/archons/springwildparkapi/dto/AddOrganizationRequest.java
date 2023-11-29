package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.OrganizationEntity;

public class AddOrganizationRequest {
    private int requesterId;
    private OrganizationEntity newOrganization;

    public AddOrganizationRequest(int requesterId, OrganizationEntity newOrganization) {
        this.requesterId = requesterId;
        this.newOrganization = newOrganization;
    }

    public AddOrganizationRequest() {
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public OrganizationEntity getNewOrganization() {
        return newOrganization;
    }

    public void setNewOrganization(OrganizationEntity newOrganization) {
        this.newOrganization = newOrganization;
    }
}
