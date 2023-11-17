package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.PaymentEntity;
import com.archons.springwildparkapi.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentEntity> getPaymentsByAccount(AccountEntity account) {
        Iterable<PaymentEntity> iterable = paymentRepository.findAll();
        List<PaymentEntity> paymentList = new ArrayList<>();

        for (PaymentEntity payment : iterable) {
            if (payment.getPayor().equals(account)) {
                paymentList.add(payment);
            }
        }

        return paymentList;
    }

    public Optional<PaymentEntity> getPaymentById(int paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public Optional<PaymentEntity> updatePayment(PaymentEntity updatedPaymentEntity) {
        Optional<PaymentEntity> existingPayment = paymentRepository.findById(updatedPaymentEntity.getId());

        if (existingPayment.isPresent()) {
            return Optional.of(paymentRepository.save(updatedPaymentEntity));
        }

        return Optional.ofNullable(null);
    }

    public boolean deletePayment(int paymentId) {
        Optional<PaymentEntity> existingPayment = paymentRepository.findById(paymentId);

        if (existingPayment.isPresent()) {
            paymentRepository.delete(existingPayment.get());
            return true;
        }

        return false;
    }
}
