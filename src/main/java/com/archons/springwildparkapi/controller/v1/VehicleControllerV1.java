package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.dto.AddVehicleRequest;
import com.archons.springwildparkapi.dto.UpdateVehicleRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.VehicleNotFoundException;
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
    public ResponseEntity<?> addVehicle(@RequestBody AddVehicleRequest request,
            @RequestHeader(name = "Authorization") String authorization) {

        try {
            return ResponseEntity.ok(vehicleService.addVehicle(request, authorization));
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete vehicle in request");
        } catch (DuplicateEntityException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vehicle already exists");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getVehicleById(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int vehicleId) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleById(authorization, vehicleId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (VehicleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<?> updateVehicle(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateVehicleRequest updatedVehicle, @PathVariable int vehicleId) {
        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(authorization, updatedVehicle, vehicleId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (VehicleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete vehicle in request");
        }
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int vehicleId) {
        try {
            vehicleService.deleteVehicle(authorization, vehicleId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (VehicleNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

}
