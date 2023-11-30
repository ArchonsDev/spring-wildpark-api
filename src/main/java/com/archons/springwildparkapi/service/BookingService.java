package com.archons.springwildparkapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.UpdateBookingRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.BookingRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final AccountService accountService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, AccountService accountService) {
        this.bookingRepository = bookingRepository;
        this.accountService = accountService;
    }

    public BookingEntity getBookingById(String authorization, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<BookingEntity> existingBooking = bookingRepository.findById(bookingId);

        if (!existingBooking.isPresent()) {
            throw new BookingNotFoundException();
        }

        BookingEntity booking = existingBooking.get();

        if (booking.getBooker() != requester && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return booking;
    }

    public BookingEntity addBooking(String authorization, BookingEntity newBooking)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);

        if (!requester.equals(newBooking.getBooker()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        newBooking.setBooker(requester);

        return bookingRepository.save(newBooking);
    }

    public BookingEntity updateBooking(String authorization, UpdateBookingRequest request, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        BookingEntity updatedBooking = request.getUpdatedBooking();

        if (!requester.equals(updatedBooking.getBooker()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        BookingEntity booking = getBookingById(authorization, bookingId);

        if (updatedBooking.getVehicle() != null) {
            booking.setVehicle(updatedBooking.getVehicle());
        }

        if (updatedBooking.getDeleted() != booking.getDeleted()) {
            booking.setDeleted(updatedBooking.getDeleted());
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String authorization, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<BookingEntity> existingBooking = bookingRepository.findById(bookingId);

        if (!existingBooking.isPresent()) {
            throw new BookingNotFoundException();
        }

        BookingEntity booking = existingBooking.get();

        if (booking.getBooker() != requester && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        booking.setDeleted(true);

        bookingRepository.save(booking);
    }
}
