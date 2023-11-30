package com.archons.springwildparkapi.dto;

public class UpdateVehicleRequest {
    private String color;
    private String plateNumber;

    public UpdateVehicleRequest(String color, String plateNumber) {
        this.color = color;
        this.plateNumber = plateNumber;
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
