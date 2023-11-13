package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleEntity> getAllVehicles() {
        Iterable<VehicleEntity> iterable = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();
        iterable.forEach(vehicleList::add);
        return vehicleList;
    }

    public Optional<VehicleEntity> getVehicleById(int vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public Optional<VehicleEntity> updateVehicle(VehicleEntity updatedvehicle) {
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(updatedvehicle.getId());

        if (existingVehicle.isPresent()) {
            return Optional.of(vehicleRepository.save(updatedvehicle));
        }

        return Optional.ofNullable(null);
    }

    public boolean deleteVehicle(int vehicleId) {
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId);

        if (existingVehicle.isPresent()) {
            vehicleRepository.delete(existingVehicle.get());
            return true;
        }

        return false;
    }

}
