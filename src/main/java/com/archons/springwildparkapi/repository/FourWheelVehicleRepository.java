package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.FourWheelVehicleEntity;

@Repository
public interface FourWheelVehicleRepository extends CrudRepository<FourWheelVehicleEntity, Integer> {

}
