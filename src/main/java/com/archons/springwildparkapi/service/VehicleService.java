package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.VehicleAlreadyExistsException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Optional<VehicleEntity> addVehicle(AccountEntity requester, VehicleEntity newVehicle)
            throws VehicleAlreadyExistsException {
        List<VehicleEntity> vehicleList = requester.getVehicles();
        boolean isUnique = true;

        for (VehicleEntity vehicle : vehicleList) {
            if (vehicle.equals(newVehicle)) {
                isUnique = false;
                break;
            }
        }

        if (!isUnique) {
            throw new VehicleAlreadyExistsException();
        }

        return Optional.of(vehicleRepository.save(newVehicle));
    }

    public List<VehicleEntity> getAllVehicles() {
        Iterable<VehicleEntity> iterable = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();
        iterable.forEach(vehicleList::add);
        return vehicleList;
    }

    public Optional<VehicleEntity> getVehicleById(AccountEntity requester, int vehicleId)
            throws InsufficientPrivilegesException {
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId);

        if (!existingVehicle.isPresent()) {
            existingVehicle = Optional.ofNullable(null);
        }

        if (!requester.equals(existingVehicle.get().getOwner()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return existingVehicle;
    }

    public Optional<VehicleEntity> updateVehicle(AccountEntity requester, VehicleEntity updatedvehicle)
            throws InsufficientPrivilegesException {
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(updatedvehicle.getId());

        if (!existingVehicle.isPresent()) {
            existingVehicle = Optional.ofNullable(null);
        }

        if (!requester.equals(updatedvehicle.getOwner()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        existingVehicle = Optional.of(vehicleRepository.save(updatedvehicle));

        return existingVehicle;
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
