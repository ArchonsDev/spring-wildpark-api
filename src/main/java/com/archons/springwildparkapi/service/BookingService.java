package com.archons.springwildparkapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.BookingUpdateRequest;
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

    public Optional<BookingEntity> getBookingById(int requesterId, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountService.getAccountById(requesterId, requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();
        Optional<BookingEntity> existingBooking = bookingRepository.findById(bookingId);

        if (!existingBooking.isPresent()) {
            throw new BookingNotFoundException();
        }

        BookingEntity booking = existingBooking.get();

        if (booking.getBooker() != requester && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(booking);
    }

    public Optional<BookingEntity> addBooking(int requesterId, BookingEntity newBooking)
            throws InsufficientPrivilegesException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountService.getAccountById(requesterId, requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();

        if (!requester.equals(newBooking.getBooker()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(bookingRepository.save(newBooking));
    }

    public Optional<BookingEntity> updateBooking(BookingUpdateRequest bookingUpdateRequest, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountService.getAccountById(bookingUpdateRequest.getRequesterId(),
                bookingUpdateRequest.getRequesterId());

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();
        BookingEntity updatedBooking = bookingUpdateRequest.getUpdatedBooking();

        if (!requester.equals(updatedBooking.getBooker()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<BookingEntity> existingBooking = getBookingById(bookingUpdateRequest.getRequesterId(), bookingId);

        if (!existingBooking.isPresent()) {
            throw new BookingNotFoundException();
        }

        BookingEntity booking = existingBooking.get();

        if (updatedBooking.getVehicle() != null) {
            booking.setVehicle(updatedBooking.getVehicle());
        }

        if (updatedBooking.getDeleted() != booking.getDeleted()) {
            booking.setDeleted(updatedBooking.getDeleted());
        }

        return Optional.of(bookingRepository.save(booking));
    }

    public void deleteBooking(int requesterId, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException, AccountNotFoundException {
        Optional<AccountEntity> existingRequester = accountService.getAccountById(requesterId, requesterId);

        if (!existingRequester.isPresent()) {
            throw new AccountNotFoundException();
        }

        AccountEntity requester = existingRequester.get();
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
