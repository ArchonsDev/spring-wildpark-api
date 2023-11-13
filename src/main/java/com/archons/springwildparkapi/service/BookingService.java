package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.repository.BookingRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingEntity> getBookingsByAccount(AccountEntity booker) {
        Iterable<BookingEntity> iterable = bookingRepository.findAll();
        List<BookingEntity> bookingList = new ArrayList<>();

        for (BookingEntity booking : iterable) {
            if (booking.getBooker().equals(booker)) {
                bookingList.add(booking);
            }
        }

        return bookingList;
    }

    public List<BookingEntity> getBookingByArea(ParkingAreaEntity parkingArea) {
        Iterable<BookingEntity> iterable = bookingRepository.findAll();
        List<BookingEntity> bookingList = new ArrayList<>();

        for (BookingEntity booking : iterable) {
            if (booking.getArea().equals(parkingArea)) {
                bookingList.add(booking);
            }
        }

        return bookingList;
    }

    public Optional<BookingEntity> getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public Optional<BookingEntity> updateBooking(BookingEntity updatedBooking) {
        Optional<BookingEntity> existingBooking = bookingRepository.findById(updatedBooking.getId());

        if (existingBooking.isPresent()) {
            return Optional.of(bookingRepository.save(updatedBooking));
        }

        return Optional.ofNullable(null);
    }

    public boolean deleteBooking(int bookingId) {
        Optional<BookingEntity> existingBooking = bookingRepository.findById(bookingId);

        if (existingBooking.isPresent()) {
            bookingRepository.delete(existingBooking.get());
            return true;
        }

        return false;
    }
}
