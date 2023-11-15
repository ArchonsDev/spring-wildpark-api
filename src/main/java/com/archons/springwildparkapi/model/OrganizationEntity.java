package com.archons.springwildparkapi.model;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Enumerated(EnumType.STRING)
    private PaymentStrategy paymentStrategy;

    @Enumerated(EnumType.STRING)
    private OrganizationType type;

    @OneToMany(mappedBy = "organization")
    private List<OrganizationAccountEntity> organizationAccounts;

    public OrganizationEntity() {
    }

    public OrganizationEntity(int id, String name, float latitude, float longitude, PaymentStrategy paymentStrategy,
            OrganizationType type, List<AccountEntity> admins, List<AccountEntity> members,
            List<OrganizationAccountEntity> organizationAccounts) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.paymentStrategy = paymentStrategy;
        this.type = type;
        this.organizationAccounts = organizationAccounts;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    public List<OrganizationAccountEntity> getOrganizationAccounts() {
        return organizationAccounts;
    }

    public void setOrganizationAccounts(List<OrganizationAccountEntity> organizationAccounts) {
        this.organizationAccounts = organizationAccounts;
    }

    public List<AccountEntity> getAccounts() {
        List<AccountEntity> accounts = getOrganizationAccounts().stream()
                .map(OrganizationAccountEntity::getAccount)
                .collect(Collectors.toList());

        return accounts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        result = prime * result + ((paymentStrategy == null) ? 0 : paymentStrategy.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((organizationAccounts == null) ? 0 : organizationAccounts.hashCode());
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
        if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
            return false;
        if (Float.floatToIntBits(longitude) != Float.floatToIntBits(other.longitude))
            return false;
        if (paymentStrategy != other.paymentStrategy)
            return false;
        if (type != other.type)
            return false;
        if (organizationAccounts == null) {
            if (other.organizationAccounts != null)
                return false;
        } else if (!organizationAccounts.equals(other.organizationAccounts))
            return false;
        return true;
    }
}
