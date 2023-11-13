package com.archons.springwildparkapi.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblfourwheelvehicle")
@DiscriminatorValue("four_wheel")
public class FourWheelVehicleEntity extends VehicleEntity {
    @Enumerated(EnumType.STRING)
    private FourWheelVehicleType type;

    public FourWheelVehicleEntity() {
        super();
    }

    public FourWheelVehicleEntity(int id, String make, String model, String plateNumber, String color,
            AccountEntity owner, FourWheelVehicleType type) {
        super(id, make, model, plateNumber, color, owner);
        this.type = type;
    }

    public FourWheelVehicleType getType() {
        return type;
    }

    public void setType(FourWheelVehicleType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        FourWheelVehicleEntity other = (FourWheelVehicleEntity) obj;
        if (type != other.type)
            return false;
        return true;
    }
}
