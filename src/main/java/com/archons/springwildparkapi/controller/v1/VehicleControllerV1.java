package com.archons.springwildparkapi.controller.v1;

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

import com.archons.springwildparkapi.dto.requests.AddVehicleRequest;
import com.archons.springwildparkapi.dto.requests.UpdateVehicleRequest;
import com.archons.springwildparkapi.service.VehicleService;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleControllerV1 {
    private final VehicleService vehicleService;

    public VehicleControllerV1(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllvehicles(@RequestHeader(name = "Authorization") String authorization)
            throws Exception {
        return ResponseEntity.ok(vehicleService.getAllVehicles(authorization));
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getVehicleById(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int vehicleId) throws Exception {
        return ResponseEntity.ok(vehicleService.getVehicleById(authorization, vehicleId));
    }

    @PostMapping("/")
    public ResponseEntity<?> addVehicle(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody AddVehicleRequest request) throws Exception {
        return ResponseEntity.ok(vehicleService.addVehicle(authorization, request));
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<?> updateVehicle(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateVehicleRequest request, @PathVariable int vehicleId) throws Exception {
        return ResponseEntity.ok(vehicleService.updateVehicle(authorization, request, vehicleId));
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int vehicleId) throws Exception {
        vehicleService.deleteVehicle(authorization, vehicleId);
        return ResponseEntity.ok().build();
    }
}
