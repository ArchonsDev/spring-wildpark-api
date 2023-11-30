package com.archons.springwildparkapi.controller.v1;

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

import com.archons.springwildparkapi.dto.UpdateParkingAreaRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.service.ParkingAreaService;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingAreaControllerV1 {
    /*
     * 
     * This controller class handles all requests related to parking area
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
    public ResponseEntity<?> addParkingArea(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody ParkingAreaEntity newParkingArea) {
        try {
            return ResponseEntity.ok(parkingAreaService.addParkingArea(authorization, newParkingArea));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action.");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
    }

    @GetMapping("/{parkingAreaId}")
    public ResponseEntity<?> getParkingAreaById(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int parkingAreaId) {
        try {
            return ResponseEntity.ok(parkingAreaService.getParkingAreaById(authorization, parkingAreaId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action.");
        } catch (ParkingAreaNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking area not found.");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        }
    }

    @PutMapping("/{parkingAreaId}")
    public ResponseEntity<?> updateParkingArea(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateParkingAreaRequest request, @PathVariable int parkingAreaId) {
        try {
            return ResponseEntity.ok(parkingAreaService.updateParkingArea(authorization, request, parkingAreaId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action.");
        } catch (ParkingAreaNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking area not found.");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete parking area in request.");
        }
    }

    @DeleteMapping("/{parkingAreaId}")
    public ResponseEntity<?> deleteParkingArea(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int parkingAreaId) {
        try {
            parkingAreaService.deleteParkingArea(authorization, parkingAreaId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ParkingAreaNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
