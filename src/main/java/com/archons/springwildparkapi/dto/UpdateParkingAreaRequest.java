package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.ParkingAreaEntity;

public class UpdateParkingAreaRequest {
    private ParkingAreaEntity updatedParkingArea;

    public UpdateParkingAreaRequest() {
    }

    public UpdateParkingAreaRequest(ParkingAreaEntity updatedParkingArea) {
        this.updatedParkingArea = updatedParkingArea;
    }

    public ParkingAreaEntity getUpdatedParkingArea() {
        return updatedParkingArea;
    }

    public void setUpdatedParkingArea(ParkingAreaEntity updatedParkingArea) {
        this.updatedParkingArea = updatedParkingArea;
    }
}
