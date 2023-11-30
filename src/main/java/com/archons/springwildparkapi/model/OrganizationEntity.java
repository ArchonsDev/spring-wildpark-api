package com.archons.springwildparkapi.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblorganization")
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Enumerated(EnumType.STRING)
    private PaymentStrategy paymentStrategy;

    @Enumerated(EnumType.STRING)
    private OrganizationType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AccountEntity owner;

    @ManyToMany
    @JoinTable(name = "tblorganizationadmin", joinColumns = @JoinColumn(name = "organization_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<AccountEntity> admins;

    @ManyToMany
    @JoinTable(name = "tblorganizationmember", joinColumns = @JoinColumn(name = "organization_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<AccountEntity> members;

    @OneToMany(mappedBy = "organization")
    private List<ParkingAreaEntity> parkingAreas;

    @Column(name = "is_deleted")
    private boolean deleted;

    public OrganizationEntity() {
    }

    public OrganizationEntity(int id, String name, double latitude, double longitude, PaymentStrategy paymentStrategy,
            OrganizationType type, AccountEntity owner, List<AccountEntity> admins, List<AccountEntity> members,
            List<ParkingAreaEntity> parkingAreas, boolean deleted) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.paymentStrategy = paymentStrategy;
        this.type = type;
        this.owner = owner;
        this.admins = admins;
        this.members = members;
        this.parkingAreas = parkingAreas;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    public List<AccountEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AccountEntity> admins) {
        this.admins = admins;
    }

    public List<AccountEntity> getMembers() {
        return members;
    }

    public void setMembers(List<AccountEntity> members) {
        this.members = members;
    }

    public List<ParkingAreaEntity> getParkingAreas() {
        return parkingAreas;
    }

    public void setParkingAreas(List<ParkingAreaEntity> parkingAreas) {
        this.parkingAreas = parkingAreas;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((paymentStrategy == null) ? 0 : paymentStrategy.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((admins == null) ? 0 : admins.hashCode());
        result = prime * result + ((members == null) ? 0 : members.hashCode());
        result = prime * result + ((parkingAreas == null) ? 0 : parkingAreas.hashCode());
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
        OrganizationEntity other = (OrganizationEntity) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        if (paymentStrategy != other.paymentStrategy)
            return false;
        if (type != other.type)
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (admins == null) {
            if (other.admins != null)
                return false;
        } else if (!admins.equals(other.admins))
            return false;
        if (members == null) {
            if (other.members != null)
                return false;
        } else if (!members.equals(other.members))
            return false;
        if (parkingAreas == null) {
            if (other.parkingAreas != null)
                return false;
        } else if (!parkingAreas.equals(other.parkingAreas))
            return false;
        if (deleted != other.deleted)
            return false;
        return true;
    }
}
