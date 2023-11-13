package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.ParkingAreaEntity;

@Repository
public interface ParkingAreaRepository extends CrudRepository<ParkingAreaEntity, Integer> {

}
