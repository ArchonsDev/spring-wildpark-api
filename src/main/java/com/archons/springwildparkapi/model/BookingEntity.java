package com.archons.springwildparkapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblbooking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private VehicleEntity vehicle;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "duration")
    private float duration;

    @OneToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parking_area_id")
    private ParkingAreaEntity area;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "booker_id")
    private AccountEntity booker;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public BookingEntity() {
    }

    public BookingEntity(int id, VehicleEntity vehicle, LocalDateTime date, float duration,
            OrganizationEntity organization, ParkingAreaEntity area, PaymentEntity payment, AccountEntity booker,
            boolean deleted, BookingStatus status) {
        this.id = id;
        this.vehicle = vehicle;
        this.date = date;
        this.duration = duration;
        this.organization = organization;
        this.area = area;
        this.payment = payment;
        this.booker = booker;
        this.deleted = deleted;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public ParkingAreaEntity getArea() {
        return area;
    }

    public void setArea(ParkingAreaEntity area) {
        this.area = area;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public AccountEntity getBooker() {
        return booker;
    }

    public void setBooker(AccountEntity booker) {
        this.booker = booker;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @JsonProperty("vehicle")
    public String getVehiclePlateNumber() {
        return vehicle != null ? vehicle.getPlateNumber() : "Unknown";
    }

    @JsonProperty("parkingArea")
    public int getParkingAreaId() {
        return area != null ? area.getId() : 0;
    }

    @JsonProperty("paymentId")
    public int getPaymentId() {
        return payment != null ? payment.getId() : 0;
    }

    @JsonProperty("booker")
    public String getBookerEmail() {
        return booker != null ? booker.getEmail() : "Unknown";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + Float.floatToIntBits(duration);
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        result = prime * result + ((area == null) ? 0 : area.hashCode());
        result = prime * result + ((payment == null) ? 0 : payment.hashCode());
        result = prime * result + ((booker == null) ? 0 : booker.hashCode());
        result = prime * result + (deleted ? 1231 : 1237);
        result = prime * result + ((status == null) ? 0 : status.hashCode());
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
        BookingEntity other = (BookingEntity) obj;
        if (id != other.id)
            return false;
        if (vehicle == null) {
            if (other.vehicle != null)
                return false;
        } else if (!vehicle.equals(other.vehicle))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (Float.floatToIntBits(duration) != Float.floatToIntBits(other.duration))
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        if (area == null) {
            if (other.area != null)
                return false;
        } else if (!area.equals(other.area))
            return false;
        if (payment == null) {
            if (other.payment != null)
                return false;
        } else if (!payment.equals(other.payment))
            return false;
        if (booker == null) {
            if (other.booker != null)
                return false;
        } else if (!booker.equals(other.booker))
            return false;
        if (deleted != other.deleted)
            return false;
        if (status != other.status)
            return false;
        return true;
    }
}
