package com.archons.springwildparkapi.dto.requests;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationType;
import com.archons.springwildparkapi.model.PaymentStrategy;

public class UpdateOrganizationRequest {
    private String name;
    private double latitude;
    private double longitude;
    private PaymentStrategy paymentStrategy;
    private OrganizationType organizationType;
    private AccountEntity owner;
    private boolean deleted;

    public UpdateOrganizationRequest(String name, double latitude, double longitude, PaymentStrategy paymentStrategy,
            OrganizationType organizationType, AccountEntity owner, boolean deleted) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.paymentStrategy = paymentStrategy;
        this.organizationType = organizationType;
        this.owner = owner;
        this.deleted = deleted;
    }

    public UpdateOrganizationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
