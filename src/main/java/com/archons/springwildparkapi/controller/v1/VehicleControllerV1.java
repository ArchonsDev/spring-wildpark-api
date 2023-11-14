package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.exceptions.InsufficientPrivillegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.VehicleEntity;
import com.archons.springwildparkapi.service.VehicleService;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleControllerV1 {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleControllerV1(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<VehicleEntity>> updateVehicle(@RequestBody AccountEntity requester,
            @RequestBody VehicleEntity updatedVehicle) {
        Optional<VehicleEntity> existingVehicle;

        try {
            existingVehicle = vehicleService.updateVehicle(requester, updatedVehicle);
        } catch (InsufficientPrivillegesException ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(existingVehicle);
    }

}
