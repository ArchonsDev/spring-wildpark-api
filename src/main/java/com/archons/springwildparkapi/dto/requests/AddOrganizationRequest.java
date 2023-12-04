package com.archons.springwildparkapi.dto.requests;

import com.archons.springwildparkapi.model.OrganizationType;
import com.archons.springwildparkapi.model.PaymentStrategy;

public class AddOrganizationRequest {
    private String name;
    private double latitude;
    private double longitude;
    private PaymentStrategy paymentStrategy;
    private OrganizationType type;

    public AddOrganizationRequest(String name, double latitude, double longitude, PaymentStrategy paymentStrategy,
            OrganizationType type) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.paymentStrategy = paymentStrategy;
        this.type = type;
    }

    public AddOrganizationRequest() {
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

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }
}
