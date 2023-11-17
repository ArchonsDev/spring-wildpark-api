package com.archons.springwildparkapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblvehicle")
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AccountEntity owner;

    @ManyToOne
    @JoinColumn(name = "parking_area_id")
    private ParkingAreaEntity parkingArea;

    public VehicleEntity() {
    }

    public VehicleEntity(int id, String make, String model, String plateNumber, String color, AccountEntity owner,
            ParkingAreaEntity parkingArea) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.plateNumber = plateNumber;
        this.color = color;
        this.owner = owner;
        this.parkingArea = parkingArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    public ParkingAreaEntity getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingAreaEntity parkingArea) {
        this.parkingArea = parkingArea;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((make == null) ? 0 : make.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((plateNumber == null) ? 0 : plateNumber.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((parkingArea == null) ? 0 : parkingArea.hashCode());
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
        VehicleEntity other = (VehicleEntity) obj;
        if (id != other.id)
            return false;
        if (make == null) {
            if (other.make != null)
                return false;
        } else if (!make.equals(other.make))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (plateNumber == null) {
            if (other.plateNumber != null)
                return false;
        } else if (!plateNumber.equals(other.plateNumber))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (parkingArea == null) {
            if (other.parkingArea != null)
                return false;
        } else if (!parkingArea.equals(other.parkingArea))
            return false;
        return true;
    }
}
