package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.VehicleEntity;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Integer> {

}
