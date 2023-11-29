package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.BookingEntity;

public class UpdateBookingRequest {
    private BookingEntity updatedBooking;

    public UpdateBookingRequest(BookingEntity updatedBooking) {
        this.updatedBooking = updatedBooking;
    }

    public UpdateBookingRequest() {
    }

    public BookingEntity getUpdatedBooking() {
        return updatedBooking;
    }

    public void setUpdatedBooking(BookingEntity updatedBooking) {
        this.updatedBooking = updatedBooking;
    }
}
