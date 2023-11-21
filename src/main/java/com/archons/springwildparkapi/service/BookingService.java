package com.archons.springwildparkapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.BookingNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.BookingRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Optional<BookingEntity> getBookingById(AccountEntity requester, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException {
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

    public Optional<BookingEntity> addBooking(AccountEntity requester, BookingEntity newBooking)
            throws InsufficientPrivilegesException {
        if (!requester.equals(newBooking.getBooker()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(bookingRepository.save(newBooking));
    }

    public Optional<BookingEntity> updateBooking(AccountEntity requester, BookingEntity updatedBooking, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException {
        if (!requester.equals(updatedBooking.getBooker()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        Optional<BookingEntity> existingBooking = getBookingById(requester, bookingId);

        if (!existingBooking.isPresent()) {
            throw new BookingNotFoundException();
        }

        return Optional.of(bookingRepository.save(updatedBooking));
    }

    public void deleteBooking(AccountEntity requester, int bookingId)
            throws InsufficientPrivilegesException, BookingNotFoundException {
        Optional<BookingEntity> existingBooking = bookingRepository.findById(bookingId);

        if (existingBooking.isPresent()) {
            BookingEntity booking = existingBooking.get();

            if (booking.getBooker() != requester && requester.getRole() != Role.ADMIN) {
                throw new InsufficientPrivilegesException();
            }

            bookingRepository.delete(booking);
        } else {
            throw new BookingNotFoundException();
        }
    }
}
