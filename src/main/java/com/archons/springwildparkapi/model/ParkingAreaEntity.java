package com.archons.springwildparkapi.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblparkingarea")
public class ParkingAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "slots")
    private int slots;

    @OneToMany(mappedBy = "parkingArea")
    private List<VehicleEntity> parkedVehicles;

    @OneToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    public ParkingAreaEntity() {
    }

    public ParkingAreaEntity(int id, int slots, List<VehicleEntity> parkedVehicles, OrganizationEntity organization) {
        this.id = id;
        this.slots = slots;
        this.parkedVehicles = parkedVehicles;
        this.organization = organization;
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

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + slots;
        result = prime * result + ((parkedVehicles == null) ? 0 : parkedVehicles.hashCode());
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
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
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        return true;
    }
}
