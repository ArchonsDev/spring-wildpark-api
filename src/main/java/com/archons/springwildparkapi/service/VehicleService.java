package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AddVehicleRequest;
import com.archons.springwildparkapi.dto.UpdateVehicleRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.VehicleNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.FourWheelVehicleEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.model.TwoWheelVehicleEntity;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.model.VehicleType;
import com.archons.springwildparkapi.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final AccountService accountService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, AccountService accountService) {
        this.vehicleRepository = vehicleRepository;
        this.accountService = accountService;
    }

    public VehicleEntity addVehicle(AddVehicleRequest request, String authorization)
            throws IncompleteRequestException, DuplicateEntityException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);

        if (request.getColor() == null ||
                request.getMake() == null ||
                request.getModel() == null ||
                request.getPlateNumber() == null) {
            throw new IncompleteRequestException();
        }

        VehicleEntity newVehicle;
        if (request.getType() == VehicleType.TWO_WHEEL) {
            if (request.getDisplacement() == 0) {
                throw new IncompleteRequestException();
            }

            TwoWheelVehicleEntity twoWheelVehicle = new TwoWheelVehicleEntity();
            twoWheelVehicle.setDisplacement(request.getDisplacement());
            newVehicle = twoWheelVehicle;
        } else if (request.getType() == VehicleType.FOUR_WHEEL) {
            if (request.getSize() == null) {
                throw new IncompleteRequestException();
            }

            FourWheelVehicleEntity fourWheelVehicle = new FourWheelVehicleEntity();
            fourWheelVehicle.setType(request.getSize());
            newVehicle = fourWheelVehicle;
        } else {
            throw new IncompleteRequestException();
        }
        newVehicle.setMake(request.getMake());
        newVehicle.setModel(request.getModel());
        newVehicle.setPlateNumber(request.getPlateNumber());
        newVehicle.setColor(request.getColor());
        newVehicle.setOwner(requester);

        return vehicleRepository.save(newVehicle);
    }

    public List<VehicleEntity> getAllVehicles() {
        Iterable<VehicleEntity> iterable = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();

        for (VehicleEntity veh : iterable) {
            if (!veh.getDeleted()) {
                vehicleList.add(veh);
            }
        }

        return vehicleList;
    }

    public VehicleEntity getVehicleById(String authorization, int vehicleId)
            throws InsufficientPrivilegesException, VehicleNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId);

        if (!existingVehicle.isPresent()) {
            throw new VehicleNotFoundException();
        }

        VehicleEntity vehicle = existingVehicle.get();

        if (!requester.equals(existingVehicle.get().getOwner())
                && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return vehicle;
    }

    public VehicleEntity updateVehicle(String authorization, UpdateVehicleRequest request, int vehicleId)
            throws InsufficientPrivilegesException, VehicleNotFoundException, AccountNotFoundException,
            IncompleteRequestException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        VehicleEntity updatedVehicle = request.getUpdatedVehicle();

        if (updatedVehicle.getId() == 0) {
            throw new IncompleteRequestException();
        }

        VehicleEntity vehicle = getVehicleById(authorization, vehicleId);

        if (!requester.equals(updatedVehicle.getOwner()) && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        if (updatedVehicle.getColor() != null) {
            vehicle.setColor(updatedVehicle.getColor());
        }

        if (updatedVehicle.getMake() != null) {
            vehicle.setMake(updatedVehicle.getMake());
        }

        if (updatedVehicle.getModel() != null) {
            vehicle.setModel(updatedVehicle.getModel());
        }

        if (updatedVehicle.getPlateNumber() != null) {
            vehicle.setPlateNumber(updatedVehicle.getPlateNumber());
        }

        if (updatedVehicle.getOwner() != null) {
            vehicle.setOwner(updatedVehicle.getOwner());
        }

        if (updatedVehicle.getParkingArea() != null) {
            vehicle.setParkingArea(updatedVehicle.getParkingArea());
        }

        return vehicleRepository.save(vehicle);

    }

    public void deleteVehicle(String authorization, int vehicleId)
            throws InsufficientPrivilegesException, VehicleNotFoundException, AccountNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId);

        if (!existingVehicle.isPresent()) {
            throw new VehicleNotFoundException();
        }

        VehicleEntity vehicle = existingVehicle.get();

        if (vehicle.getOwner() != requester && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        vehicle.setDeleted(true);
        vehicleRepository.save(vehicle);
    }

}
