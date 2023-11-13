package com.archons.springwildparkapi.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tlbparkingarea")
public class ParkingAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "slots")
    private int slots;

    @OneToMany
    @JoinColumn(name = "parking_area_id")
    private List<VehicleEntity> parkedVehicles;

    public ParkingAreaEntity() {
    }

    public ParkingAreaEntity(int id, int slots, List<VehicleEntity> parkedVehicles) {
        this.id = id;
        this.slots = slots;
        this.parkedVehicles = parkedVehicles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public List<VehicleEntity> getParkedVehicles() {
        return parkedVehicles;
    }

    public void setParkedVehicles(List<VehicleEntity> parkedVehicles) {
        this.parkedVehicles = parkedVehicles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + slots;
        result = prime * result + ((parkedVehicles == null) ? 0 : parkedVehicles.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParkingAreaEntity other = (ParkingAreaEntity) obj;
        if (id != other.id)
            return false;
        if (slots != other.slots)
            return false;
        if (parkedVehicles == null) {
            if (other.parkedVehicles != null)
                return false;
        } else if (!parkedVehicles.equals(other.parkedVehicles))
            return false;
        return true;
    }

}
