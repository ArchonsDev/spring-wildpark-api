package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.FourWheelVehicleType;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.model.VehicleType;

public class AddVehicleRequest {
    private String color;
    private String make;
    private String model;
    private String plateNumber;
    private VehicleType type;
    private float displacement;
    private FourWheelVehicleType size;

    public AddVehicleRequest(String color, String make, String model, String plateNumber, VehicleType type,
            float displacement, FourWheelVehicleType size) {
        this.color = color;
        this.make = make;
        this.model = model;
        this.plateNumber = plateNumber;
        this.type = type;
        this.displacement = displacement;
        this.size = size;
    }

    public AddVehicleRequest() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }

    public FourWheelVehicleType getSize() {
        return size;
    }

    public void setSize(FourWheelVehicleType size) {
        this.size = size;
    }

}
