package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.VehicleAlreadyExistsException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.service.VehicleService;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleControllerV1 {
    /*
     * This controller class handles all vehicle related requests
     * 
     * Endpoints:
     * POST /api/v1/vehicles/
     * PUT /api/v1/vehicles/{id}
     * 
     */
    private final VehicleService vehicleService;

    @Autowired
    public VehicleControllerV1(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/")
    public ResponseEntity<Optional<VehicleEntity>> addVehicle(@RequestBody AccountEntity requester,
            @RequestBody VehicleEntity vehicle) {
        try {
            return ResponseEntity.ok(vehicleService.addVehicle(requester, vehicle));
        } catch (VehicleAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<VehicleEntity>> updateVehicle(@RequestBody AccountEntity requester,
            @RequestBody VehicleEntity updatedVehicle) {
        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(requester, updatedVehicle));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
