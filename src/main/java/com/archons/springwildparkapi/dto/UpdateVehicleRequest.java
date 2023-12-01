package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.VehicleEntity;

public class UpdateVehicleRequest {

    private VehicleEntity updatedVehicle;

    public UpdateVehicleRequest() {

    }

    public UpdateVehicleRequest(VehicleEntity updatedVehicle) {
        this.updatedVehicle = updatedVehicle;
    }

    public VehicleEntity getUpdatedVehicle() {
        return updatedVehicle;
    }

    public void setUpdatedVehicle(VehicleEntity updatedVehicle) {
        this.updatedVehicle = updatedVehicle;
    }

}
