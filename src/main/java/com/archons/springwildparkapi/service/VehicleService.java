package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AddVehicleRequest;
import com.archons.springwildparkapi.dto.UpdateVehicleRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.exceptions.VehicleAlreadyExistsException;
import com.archons.springwildparkapi.exceptions.VehicleNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.FourWheelVehicleEntity;
import com.archons.springwildparkapi.model.TwoWheelVehicleEntity;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.model.VehicleType;
import com.archons.springwildparkapi.repository.VehicleRepository;

@Service
public class VehicleService extends BaseService {
    private final VehicleRepository vehicleRepository;
    private final AccountService accountService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, AccountService accountService) {
        this.vehicleRepository = vehicleRepository;
        this.accountService = accountService;
    }

    public VehicleEntity addVehicle(String authorization, AddVehicleRequest request)
            throws VehicleAlreadyExistsException, AccountNotFoundException, IncompleteRequestException {
        // Retrieve requester
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        // Validate request
        if (request.getMake() == null ||
                request.getModel() == null ||
                request.getPlateNumber() == null ||
                request.getColor() == null) {
            throw new IncompleteRequestException();
        }
        // Check uniqueness
        Optional<VehicleEntity> existingVehicle = vehicleRepository.findByPlateNumber(request.getPlateNumber());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistsException();
        }
        // Build entity
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

    public List<VehicleEntity> getAllVehicles(String authorization)
            throws AccountNotFoundException, InsufficientPrivilegesException {
        // Retrieve requester
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        // Check permissions
        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Build list
        Iterable<VehicleEntity> iterable = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();
        for (VehicleEntity v : iterable) {
            if (!v.isDeleted())
                vehicleList.add(v);
        }

        return vehicleList;
    }

    public VehicleEntity getVehicleById(String authorization, int vehicleId)
            throws InsufficientPrivilegesException, VehicleNotFoundException, AccountNotFoundException {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException());
        // Check permissions
        if (!requester.equals(vehicle.getOwner()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return vehicle;
    }

    public VehicleEntity updateVehicle(String authorization, UpdateVehicleRequest request, int vehicleId)
            throws InsufficientPrivilegesException, AccountNotFoundException, VehicleNotFoundException,
            ParkingAreaNotFoundException {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        VehicleEntity vehicle = getVehicleById(authorization, vehicleId);
        // Check request fields and update existing entity
        if (!requester.equals(vehicle.getOwner()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        if (request.getColor() != null) {
            vehicle.setColor(request.getColor());
        }
        if (request.getPlateNumber() != null) {
            vehicle.setPlateNumber(request.getPlateNumber());
        }
        if (request.isDeleted() != vehicle.isDeleted()) {
            vehicle.setDeleted(request.isDeleted());
        }

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(String authorization, int vehicleId)
            throws AccountNotFoundException, VehicleNotFoundException, InsufficientPrivilegesException {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        VehicleEntity vehicle = getVehicleById(authorization, vehicleId);
        // Check permissions
        if (!requester.equals(vehicle.getOwner()) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Set deleted
        vehicle.setDeleted(true);
        vehicleRepository.save(vehicle);
    }
}
