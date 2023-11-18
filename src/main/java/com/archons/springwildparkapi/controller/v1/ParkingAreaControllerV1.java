package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.service.ParkingAreaService;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingAreaControllerV1 {
    /*
     * 
     * This controller class hanldes all requests related to parking area
     * 
     * Endpoints:
     * POST /api/v1/parking/
     * GET /api/v1/parking/{parkingId}
     * PUT /api/v1/parking/{parkingId}
     * DELETE /api/v1/parking/{parkingId}
     * 
     */
    private ParkingAreaService parkingAreaService;

    @Autowired
    public ParkingAreaControllerV1(ParkingAreaService parkingAreaService) {
        this.parkingAreaService = parkingAreaService;
    }

    @PostMapping("/")
    public ResponseEntity<Optional<ParkingAreaEntity>> addParkingArea(@RequestBody AccountEntity requester,
            @RequestBody ParkingAreaEntity parkingArea) {
        try {
            return ResponseEntity.ok(parkingAreaService.addParkingArea(requester, parkingArea));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<Optional<ParkingAreaEntity>> getParkingAreaById(@RequestBody AccountEntity requester,
            @RequestParam int parkingId) {
        try {
            return ResponseEntity.ok(parkingAreaService.getParkingAreaById(requester, parkingId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ParkingAreaNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{parkingId}")
    public ResponseEntity<Optional<ParkingAreaEntity>> updateParkingArea(@RequestBody AccountEntity requester,
            @RequestBody ParkingAreaEntity updatedParkingArea, @RequestParam int parkingId) {
        try {
            return ResponseEntity.ok(parkingAreaService.updateParkingArea(requester, parkingId, updatedParkingArea));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ParkingAreaNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{parkingId}")
    public ResponseEntity<Void> deleteParkingArea(@RequestBody AccountEntity requester, @RequestParam int parkingId) {
        try {
            parkingAreaService.deleteParkingArea(requester, parkingId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ParkingAreaNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
