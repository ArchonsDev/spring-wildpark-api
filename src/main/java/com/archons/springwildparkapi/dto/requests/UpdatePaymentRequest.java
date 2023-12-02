package com.archons.springwildparkapi.dto.requests;

public class UpdatePaymentRequest {
    private boolean deleted;

    public UpdatePaymentRequest() {
    }

    public UpdatePaymentRequest(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
