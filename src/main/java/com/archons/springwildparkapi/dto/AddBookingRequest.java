package com.archons.springwildparkapi.dto;

import java.util.Date;

public class AddBookingRequest {
    private int vehicleId;
    private Date date;
    private float duration;
    private int organizationId;
    private int parkingAreaId;

    public AddBookingRequest(int vehicleId, Date date, float duration, int organizationId, int parkingAreaId) {
        this.vehicleId = vehicleId;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
