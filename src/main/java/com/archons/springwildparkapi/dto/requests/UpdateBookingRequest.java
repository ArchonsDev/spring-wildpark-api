package com.archons.springwildparkapi.dto.requests;

public class UpdateBookingRequest {
    private boolean deleted;

    public UpdateBookingRequest() {
    }

    public UpdateBookingRequest(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
