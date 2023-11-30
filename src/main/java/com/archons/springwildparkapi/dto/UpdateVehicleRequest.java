package com.archons.springwildparkapi.dto;

public class UpdateVehicleRequest {
    private String color;
    private String plateNumber;
    private boolean deleted;

    public UpdateVehicleRequest(String color, String plateNumber, boolean deleted) {
        this.color = color;
        this.plateNumber = plateNumber;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
}
