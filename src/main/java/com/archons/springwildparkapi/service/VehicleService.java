package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.InsufficientPrivillegesException;
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

    public List<VehicleEntity> getAllVehicles() {
        Iterable<VehicleEntity> iterable = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();
        iterable.forEach(vehicleList::add);
        return vehicleList;
    }

    public Optional<VehicleEntity> getVehicleById(AccountEntity requester, int vehicleId)
            throws InsufficientPrivillegesException {
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId);

        if (!existingVehicle.isPresent()) {
            existingVehicle = Optional.ofNullable(null);
        }

        if (!requester.equals(existingVehicle.get().getOwner()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        return existingVehicle;
    }

    public Optional<VehicleEntity> updateVehicle(AccountEntity requester, VehicleEntity updatedvehicle)
            throws InsufficientPrivillegesException {
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(updatedvehicle.getId());

        if (!existingVehicle.isPresent()) {
            existingVehicle = Optional.ofNullable(null);
        }

        if (!requester.equals(updatedvehicle.getOwner()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
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

    public List<VehicleEntity> getAllAccountVehicles(AccountEntity account, AccountEntity requester)
            throws InsufficientPrivillegesException {
        Iterable<VehicleEntity> iterable = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();

        if (!requester.equals(account) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        for (VehicleEntity vehicle : iterable) {
            if (vehicle.getOwner().equals(account)) {
                vehicleList.add(vehicle);
            }
        }

        return vehicleList;
    }

    public Optional<VehicleEntity> getAccountVehicle(AccountEntity account, AccountEntity requester, int vehicleId)
            throws InsufficientPrivillegesException {
        List<VehicleEntity> accountVehicles = getAllAccountVehicles(account, requester);

        for (VehicleEntity vehicle : accountVehicles) {
            if (vehicle.getId() == vehicleId) {
                return Optional.of(vehicle);
            }
        }

        return Optional.ofNullable(null);
    }

}
