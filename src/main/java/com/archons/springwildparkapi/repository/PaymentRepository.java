package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.PaymentEntity;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Integer> {

}
