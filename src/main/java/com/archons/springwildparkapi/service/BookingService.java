package com.archons.springwildparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AddBookingRequest;
import com.archons.springwildparkapi.dto.UpdateBookingRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.UnknownParkingAreaException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.BookingRepository;

@Service
public class BookingService extends BaseService {
    private final BookingRepository bookingRepository;
    private final AccountService accountService;
    private final VehicleService vehicleService;
    private final OrganizationService orgService;
    private final ParkingAreaService parkingService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, AccountService accountService,
            VehicleService vehicleService, OrganizationService orgService, ParkingAreaService parkingService) {
        this.bookingRepository = bookingRepository;
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

    public BookingEntity addBooking(String authorization, AddBookingRequest request)
            throws AccountNotFoundException, IncompleteRequestException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        try {
            VehicleEntity vehicle = vehicleService.getVehicleById(authorization, request.getVehicleId());
            OrganizationEntity organization = orgService.getOrganizationById(request.getOrganizationId());
            // ParkingAreaEntity parkingArea =
            // parkingService.getParkingAreaById(authorization, request.getParkingAreaId());

            BookingEntity newBooking = new BookingEntity();

            newBooking.setVehicle(vehicle);
            newBooking.setDate(request.getDate());
            newBooking.setDuration(request.getDuration());
            newBooking.setOrganization(organization);

            // if (!organization.equals(parkingArea.getOrganization())) {
            // throw new UnknownParkingAreaException();
            // }

            // newBooking.setArea(parkingArea);
            newBooking.setBooker(requester);

            return bookingRepository.save(newBooking);
        } catch (Exception ex) {
            throw new IncompleteRequestException();
        }
    }

    public BookingEntity updateBooking(String authorization, UpdateBookingRequest request, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException());

        if (!requester.equals(booking.getBooker()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        if (request.getPayment() != null) {
            booking.setPayment(request.getPayment());
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

        if (!requester.equals(booking.getBooker()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        booking.setDeleted(true);
        bookingRepository.save(booking);
    }
}
