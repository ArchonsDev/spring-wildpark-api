package com.archons.springwildparkapi.dto;

public class UpdateParkingAreaRequest {
    private int slots;
    private boolean deleted;

    public UpdateParkingAreaRequest(int slots, boolean deleted) {
        this.slots = slots;
        this.deleted = deleted;
    }

    public UpdateParkingAreaRequest() {
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
