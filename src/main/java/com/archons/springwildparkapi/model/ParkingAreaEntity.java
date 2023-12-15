package com.archons.springwildparkapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "area")
    private List<BookingEntity> bookings;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @JsonIgnore
    private OrganizationEntity organization;

    @Column(name = "is_deleted")
    private boolean deleted;

    public ParkingAreaEntity() {
    }

    public ParkingAreaEntity(int id, int slots, List<VehicleEntity> parkedVehicles, List<BookingEntity> bookings,
            OrganizationEntity organization,
            boolean deleted) {
        this.id = id;
        this.slots = slots;
        this.parkedVehicles = parkedVehicles;
        this.bookings = bookings;
        this.organization = organization;
        this.deleted = deleted;
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

    public List<BookingEntity> getBookings() {
        List<BookingEntity> bookingList = new ArrayList<>();

        if (bookings == null)
            return bookingList;

        for (BookingEntity b : bookings) {
            if (!b.isDeleted()) {
                bookingList.add(b);
            }
        }
        return bookingList;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonProperty("organization")
    public String getOrganizationName() {
        return organization != null ? organization.getName() : "Unknown";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + slots;
        result = prime * result + ((parkedVehicles == null) ? 0 : parkedVehicles.hashCode());
        result = prime * result + ((bookings == null) ? 0 : bookings.hashCode());
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        result = prime * result + (deleted ? 1231 : 1237);
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
        if (bookings == null) {
            if (other.bookings != null)
                return false;
        } else if (!bookings.equals(other.bookings))
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        if (deleted != other.deleted)
            return false;
        return true;
    }
}
