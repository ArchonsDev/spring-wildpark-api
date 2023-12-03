package com.archons.springwildparkapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.VehicleEntity;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Integer> {
    Optional<VehicleEntity> findByPlateNumber(String plateNumber);
}
