package com.archons.springwildparkapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.BookingEntity;
import com.archons.springwildparkapi.model.PaymentEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {
    Optional<BookingEntity> findByPayment(PaymentEntity payment);
}
