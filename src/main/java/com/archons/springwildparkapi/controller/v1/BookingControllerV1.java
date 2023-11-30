package com.archons.springwildparkapi.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.UpdateBookingRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
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
    public ResponseEntity<?> addBooking(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody BookingEntity newBooking) {
        try {
            return ResponseEntity.ok(bookingService.addBooking(authorization, newBooking));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int bookingId) {
        try {
            return ResponseEntity.ok(bookingService.getBookingById(authorization, bookingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateBookingRequest request,
            @PathVariable int bookingId) {
        try {
            return ResponseEntity.ok(bookingService.updateBooking(authorization, request, bookingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int bookingId) {
        try {
            bookingService.deleteBooking(authorization, bookingId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }
}
