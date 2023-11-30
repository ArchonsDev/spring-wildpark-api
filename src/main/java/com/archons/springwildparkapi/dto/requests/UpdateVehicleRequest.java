package com.archons.springwildparkapi.dto.requests;

public class UpdateVehicleRequest {
    private String color;
    private String plateNumber;
    private boolean deleted;
    private int parkingAreaId;

    public UpdateVehicleRequest(String color, String plateNumber, boolean deleted, int parkingAreaId) {
        this.color = color;
        this.plateNumber = plateNumber;
        this.deleted = deleted;
        this.parkingAreaId = parkingAreaId;
    }

    public UpdateVehicleRequest() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getParkingAreaId() {
        return parkingAreaId;
    }

    public void setParkingAreaId(int parkingAreaId) {
        this.parkingAreaId = parkingAreaId;
    }
}
