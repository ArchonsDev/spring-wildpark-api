package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.service.BookingService;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingControllerV1 {
    /*
     * This controller class handles all booking related requests
     * 
     * Endpoints:
     * POST /api/v1/bookings/
     * GET /api/v1/bookings/{bookingId}
     * PUT /api/v1/bookings/{bookingId}
     * DELETE /api/v1/bookings/{bookingId}
     * 
     */
    private BookingService bookingService;

    @Autowired
    public BookingControllerV1(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResponseEntity<Optional<BookingEntity>> addBooking(@RequestBody AccountEntity requester,
            @RequestBody BookingEntity newBooking) {
        try {
            return ResponseEntity.ok(bookingService.addBooking(requester, newBooking));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Optional<BookingEntity>> getBookingById(@RequestBody AccountEntity requester,
            @RequestParam int bookingId) {
        try {
            return ResponseEntity.ok(bookingService.getBookingById(requester, bookingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Optional<BookingEntity>> updateBooking(@RequestBody AccountEntity requester,
            @RequestBody BookingEntity updatedBooking, @RequestParam int bookingId) {
        try {
            return ResponseEntity.ok(bookingService.updateBooking(requester, updatedBooking, bookingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@RequestBody AccountEntity requester,
            @RequestParam int bookingId) {
        try {
            bookingService.deleteBooking(requester, bookingId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
