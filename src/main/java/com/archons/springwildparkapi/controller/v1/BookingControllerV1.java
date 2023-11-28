package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.BookingUpdateRequest;
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
     * POST /api/v1/bookings?requesterId=
     * GET /api/v1/bookings/{bookingId}?requesterId=
     * PUT /api/v1/bookings/{bookingId}
     * DELETE /api/v1/bookings/{bookingId}?requesterId=
     * 
     */
    private BookingService bookingService;

    @Autowired
    public BookingControllerV1(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResponseEntity<Optional<BookingEntity>> addBooking(@RequestParam int requesterId,
            @RequestBody BookingEntity newBooking) {
        try {
            return ResponseEntity.ok(bookingService.addBooking(requesterId, newBooking));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Optional<BookingEntity>> getBookingById(@RequestParam int requesterId,
            @PathVariable int bookingId) {
        try {
            return ResponseEntity.ok(bookingService.getBookingById(requesterId, bookingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Optional<BookingEntity>> updateBooking(@RequestBody BookingUpdateRequest bookingUpdateRequest,
            @PathVariable int bookingId) {
        try {
            return ResponseEntity.ok(bookingService.updateBooking(bookingUpdateRequest, bookingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@RequestParam int requesterId,
            @PathVariable int bookingId) {
        try {
            bookingService.deleteBooking(requesterId, bookingId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
