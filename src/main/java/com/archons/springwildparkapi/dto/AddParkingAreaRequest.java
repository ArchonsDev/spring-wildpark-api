package com.archons.springwildparkapi.dto;

public class AddParkingAreaRequest {
    private int slots;
    private int organizationId;

    public AddParkingAreaRequest(int slots, int organizationId) {
        this.slots = slots;
        this.organizationId = organizationId;
    }

    public AddParkingAreaRequest() {
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
