package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.BookingEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {

}
