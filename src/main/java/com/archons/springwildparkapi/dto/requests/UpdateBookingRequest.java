package com.archons.springwildparkapi.dto.requests;

import com.archons.springwildparkapi.model.PaymentEntity;

public class UpdateBookingRequest {
    private PaymentEntity payment;
    private boolean deleted;

    public UpdateBookingRequest() {
    }

    public UpdateBookingRequest(PaymentEntity payment, boolean deleted) {
        this.payment = payment;
        this.deleted = deleted;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
