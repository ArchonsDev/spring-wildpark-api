package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.TwoWheelVehicleEntity;

@Repository
public interface TwoWheelVehicleRepository extends CrudRepository<TwoWheelVehicleEntity, Integer> {

}
