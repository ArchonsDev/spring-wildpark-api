package com.archons.springwildparkapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.MaxCapacityReachedException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.exceptions.PaymentNotFoundException;
import com.archons.springwildparkapi.exceptions.UnknownParkingAreaException;
import com.archons.springwildparkapi.exceptions.VehicleAlreadyExistsException;
import com.archons.springwildparkapi.exceptions.VehicleNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleBookingNotFoundException(BookingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleVehicleNotFoundException(VehicleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleOrganizationNotFoundException(OrganizationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
    }

    @ExceptionHandler(ParkingAreaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleParkingAreaNotFoundException(ParkingAreaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking area not found");
    }

    @ExceptionHandler(UnknownParkingAreaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUnknownParkingAreaException(UnknownParkingAreaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown parking area");
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleConflicException(DuplicateEntityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity already exists");
    }

    @ExceptionHandler(IncompleteRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(IncompleteRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    @ExceptionHandler(InsufficientPrivilegesException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleForbiddenException(InsufficientPrivilegesException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
    }

    @ExceptionHandler(MaxCapacityReachedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMaxCapacityReachedException(MaxCapacityReachedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max capacity reached");
    }

    @ExceptionHandler(VehicleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleVehicleAlreadyExistsException(VehicleAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Vehicle already exists");
    }
}