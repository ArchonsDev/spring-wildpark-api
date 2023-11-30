package com.archons.springwildparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AddBookingRequest;
import com.archons.springwildparkapi.dto.UpdateBookingRequest;
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
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.BookingStatus;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.BookingRepository;
import com.archons.springwildparkapi.repository.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService extends BaseService {
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final AccountService accountService;
    private final VehicleService vehicleService;
    private final OrganizationService orgService;
    private final ParkingAreaService parkingService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, VehicleRepository vehicleRepository,
            AccountService accountService, VehicleService vehicleService, OrganizationService orgService,
            ParkingAreaService parkingService) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.accountService = accountService;
        this.vehicleService = vehicleService;
        this.orgService = orgService;
        this.parkingService = parkingService;
    }

    public BookingEntity getBookingById(String authorization, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException());

        if (!requester.equals(booking.getBooker()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return booking;
    }

    @Transactional
    public BookingEntity addBooking(String authorization, AddBookingRequest request)
            throws AccountNotFoundException, IncompleteRequestException, InsufficientPrivilegesException,
            VehicleNotFoundException, OrganizationNotFoundException, ParkingAreaNotFoundException,
            UnknownParkingAreaException, DuplicateEntityException, MaxCapacityReachedException {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        VehicleEntity vehicle = vehicleService.getVehicleById(authorization, request.getVehicleId());
        OrganizationEntity organization = orgService.getOrganizationById(request.getOrganizationId());
        ParkingAreaEntity parkingArea = parkingService.getParkingAreaById(request.getParkingAreaId());

        BookingEntity newBooking = new BookingEntity();
        // Check for vehicle ownership & admin
        if (!requester.equals(vehicle.getOwner()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Check for parking area ownership
        if (!organization.equals(parkingArea.getOrganization())) {
            throw new UnknownParkingAreaException();
        }
        // Check if vehicle has been booked
        if (vehicle.getParkingArea() != null) {
            throw new DuplicateEntityException();
        }
        // Check if space is still available
        if (parkingArea.getParkedVehicles().size() >= parkingArea.getSlots()) {
            throw new MaxCapacityReachedException();
        }
        // Set fieldss
        newBooking.setVehicle(vehicle);
        newBooking.setDate(parseDateTime(request.getdateTime()));
        newBooking.setDuration(request.getDuration());
        newBooking.setOrganization(organization);
        newBooking.setArea(parkingArea);
        newBooking.setBooker(requester);
        newBooking.setStatus(BookingStatus.PENDING_PAYMENT); // Initial status value
        // Update vehicle parking
        vehicle.setParkingArea(parkingArea);
        vehicleRepository.save(vehicle);

        return bookingRepository.save(newBooking);
    }

    public BookingEntity updateBooking(String authorization, UpdateBookingRequest request, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException());
        // Check permissions
        if (!requester.equals(booking.getBooker()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Updateable fields
        if (request.getPayment() != null) {
            booking.setPayment(request.getPayment());
            booking.setStatus(BookingStatus.CONFIRMED);
        }
        if (request.isDeleted() != booking.isDeleted()) {
            booking.setDeleted(request.isDeleted());
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String authorization, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException());
        // Check permissions
        if (!requester.equals(booking.getBooker()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Realease vehicle parking area
        VehicleEntity vehicle = booking.getVehicle();
        vehicle.setParkingArea(null);
        vehicleRepository.save(vehicle);

        booking.setDeleted(true);
        bookingRepository.save(booking);
    }
}
