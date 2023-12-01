package com.archons.springwildparkapi.controller.v1;

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

import com.archons.springwildparkapi.dto.requests.AddBookingRequest;
import com.archons.springwildparkapi.dto.requests.UpdateBookingRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.MaxCapacityReachedException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.exceptions.UnknownParkingAreaException;
import com.archons.springwildparkapi.exceptions.VehicleNotFoundException;
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

    public BookingControllerV1(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResponseEntity<BookingEntity> addBooking(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody AddBookingRequest request)
            throws AccountNotFoundException, IncompleteRequestException, InsufficientPrivilegesException,
            VehicleNotFoundException, OrganizationNotFoundException, ParkingAreaNotFoundException,
            UnknownParkingAreaException, DuplicateEntityException, MaxCapacityReachedException {
        return ResponseEntity.ok(bookingService.addBooking(authorization, request));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        return ResponseEntity.ok(bookingService.getBookingById(authorization, bookingId));
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateBookingRequest request,
            @PathVariable int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        return ResponseEntity.ok(bookingService.updateBooking(authorization, request, bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
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
