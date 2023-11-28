package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.BookingEntity;

public class BookingUpdateRequest {
    private int requesterId;
    private BookingEntity updatedBooking;

    public BookingUpdateRequest(int requesterId, BookingEntity updatedBooking) {
        this.requesterId = requesterId;
        this.updatedBooking = updatedBooking;
    }

    public BookingUpdateRequest() {
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public BookingEntity getUpdatedBooking() {
        return updatedBooking;
    }

    public void setUpdatedBooking(BookingEntity updatedBooking) {
        this.updatedBooking = updatedBooking;
    }
}
