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
import jakarta.persistence.Table;

@Entity
@Table(name = "tblpayment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount")
    private float amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "payor_id")
    @JsonIgnore
    private AccountEntity payor;

    @Column(name = "is_deleted")
    private boolean deleted;

    public PaymentEntity() {
    }

    public PaymentEntity(int id, float amount, PaymentType paymentType, LocalDateTime date, AccountEntity payor) {
        this.id = id;
        this.amount = amount;
        this.paymentType = paymentType;
        this.date = date;
        this.payor = payor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AccountEntity getPayor() {
        return payor;
    }

    public void setPayor(AccountEntity payor) {
        this.payor = payor;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonProperty("payor")
    public String getPayorEmail() {
        return payor != null ? payor.getEmail() : "Unknown";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + Float.floatToIntBits(amount);
        result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((payor == null) ? 0 : payor.hashCode());
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
        PaymentEntity other = (PaymentEntity) obj;
        if (id != other.id)
            return false;
        if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
            return false;
        if (paymentType != other.paymentType)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (payor == null) {
            if (other.payor != null)
                return false;
        } else if (!payor.equals(other.payor))
            return false;
        if (deleted != other.deleted)
            return false;
        return true;
    }
}
