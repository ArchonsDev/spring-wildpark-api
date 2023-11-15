package com.archons.springwildparkapi.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.service.BookingService;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingControllerV1 {
    /*
     * This controller class handles all booking related requests
     */
    private BookingService bookingService;

    @Autowired
    public BookingControllerV1(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
