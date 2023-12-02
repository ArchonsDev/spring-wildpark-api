package com.archons.springwildparkapi.dto.requests;

import com.archons.springwildparkapi.model.PaymentType;

public class AddPaymentRequest {
    private float amount;
    private PaymentType type;
    private String date;
    private int payorId;
    private int bookingId;

    public AddPaymentRequest(float amount, PaymentType type, String date, int payorId, int bookingId) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.payorId = payorId;
        this.bookingId = bookingId;
    }

    public AddPaymentRequest() {
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayorId() {
        return payorId;
    }

    public void setPayorId(int payorId) {
        this.payorId = payorId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
