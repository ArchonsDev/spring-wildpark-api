package com.archons.springwildparkapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbltwowheelvehicle")
public class TwoWheelVehicleEntity extends VehicleEntity {
    @Column(name = "displacement")
    private float displacement;

    public TwoWheelVehicleEntity() {
    }

    public TwoWheelVehicleEntity(int id, String make, String model, String plateNumber, String color,
            AccountEntity owner, float displacement) {
        super(id, make, model, plateNumber, color, owner);
        this.displacement = displacement;
    }

    public float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Float.floatToIntBits(displacement);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TwoWheelVehicleEntity other = (TwoWheelVehicleEntity) obj;
        if (Float.floatToIntBits(displacement) != Float.floatToIntBits(other.displacement))
            return false;
        return true;
    }
}
