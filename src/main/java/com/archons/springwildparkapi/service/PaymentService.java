package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.requests.AddPaymentRequest;
import com.archons.springwildparkapi.dto.requests.UpdatePaymentRequest;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.PaymentNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.BookingStatus;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService extends BaseService {
    private final PaymentRepository paymentRepository;
    private final AccountService accountService;
    private final BookingService bookingService;

    public PaymentService(PaymentRepository paymentRepository, AccountService accountService,
            BookingService bookingService) {
        this.paymentRepository = paymentRepository;
        this.accountService = accountService;
        this.bookingService = bookingService;
    }

    public List<PaymentEntity> getAllPayments(String authorization) throws Exception {
        // Retreive requester
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        // Check permissions
        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Build payment list
        Iterable<PaymentEntity> iterable = paymentRepository.findAll();
        List<PaymentEntity> paymentList = new ArrayList<>();
        iterable.forEach(paymentList::add);

        return paymentList;
    }

    public PaymentEntity getPaymentById(String authorization, int paymentId) throws Exception {
        // Retrive requester
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        PaymentEntity payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException());

        // Check permissions
        if (!requester.equals(payment.getPayor()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return payment;
    }

    public PaymentEntity updatePayment(String authorization, UpdatePaymentRequest request, int paymentId)
            throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        PaymentEntity payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException());
        // Check permissions
        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Update payment
        if (request.isDeleted() != payment.isDeleted()) {
            payment.setDeleted(request.isDeleted());
        }

        return paymentRepository.save(payment);
    }

    @Transactional
    public void deletePayment(String authorization, int paymentId) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        PaymentEntity payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException());
        // Check permissions
        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Retrieve bookinng associated with payment
        Optional<BookingEntity> exisingBooking = bookingService.getBookingByPayment(payment);
        if (exisingBooking.isPresent()) {
            BookingEntity booking = exisingBooking.get();
            bookingService.deleteBookingPayment(booking);
        }
        // Update payment
        payment.setDeleted(true);
        paymentRepository.save(payment);
    }

    @Transactional
    public PaymentEntity addPayment(String authorization, AddPaymentRequest request) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        AccountEntity payor = accountService.getAccountById(authorization, request.getPayorId());
        BookingEntity booking = bookingService.getBookingById(authorization, request.getBookingId());
        // Check permissions
        if (!requester.equals(payor) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Validate form
        if (booking.getStatus() != BookingStatus.PENDING_PAYMENT ||
                request.getType() == null ||
                request.getAmount() <= 0 ||
                request.getDate() == null) {
            throw new IncompleteRequestException();
        }
        // Build entitiy
        PaymentEntity newPayment = new PaymentEntity();
        newPayment.setAmount(request.getAmount());
        newPayment.setPaymentType(request.getType());
        newPayment.setDate(parseDateTime(request.getDate()));
        newPayment.setPayor(payor);
        // Update booking
        bookingService.makePayment(booking);

        return paymentRepository.save(newPayment);
    }
}
