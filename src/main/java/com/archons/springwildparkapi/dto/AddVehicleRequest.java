package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.FourWheelVehicleType;
import com.archons.springwildparkapi.model.VehicleType;

public class AddVehicleRequest {
    private String make;
    private String model;
    private String plateNumber;
    private String color;
    private VehicleType type;
    private FourWheelVehicleType size;
    private float displacement;

    public AddVehicleRequest(String make, String model, String plateNumber, String color, VehicleType type,
            FourWheelVehicleType size, float displacement) {
        this.make = make;
        this.model = model;
        this.plateNumber = plateNumber;
        this.color = color;
        this.type = type;
        this.size = size;
        this.displacement = displacement;
    }

    public AddVehicleRequest() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public FourWheelVehicleType getSize() {
        return size;
    }

    public void setSize(FourWheelVehicleType size) {
        this.size = size;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }
}
