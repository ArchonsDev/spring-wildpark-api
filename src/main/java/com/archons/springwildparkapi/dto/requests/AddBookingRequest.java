package com.archons.springwildparkapi.dto.requests;

public class AddBookingRequest {
    private int vehicleId;
    private String dateTime;
    private float duration;
    private int organizationId;
    private int parkingAreaId;

    public AddBookingRequest(int vehicleId, String dateTime, float duration, int organizationId, int parkingAreaId) {
        this.vehicleId = vehicleId;
        this.dateTime = dateTime;
        this.duration = duration;
        this.organizationId = organizationId;
        this.parkingAreaId = parkingAreaId;
    }

    public AddBookingRequest() {
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getdateTime() {
        return dateTime;
    }

    public void setdateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getParkingAreaId() {
        return parkingAreaId;
    }

    public void setParkingAreaId(int parkingAreaId) {
        this.parkingAreaId = parkingAreaId;
    }
}
