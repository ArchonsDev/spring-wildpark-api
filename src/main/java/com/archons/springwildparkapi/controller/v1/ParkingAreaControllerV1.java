package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
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
     * TODO: POST /api/v1/parking/
     * TODO: GET /api/v1/parking/{parkingId}
     * TODO: PUT /api/v1/parking/{parkingId}
     * TODO: DELETE /api/v1/parking/{parkingId}
     * TODO: GET /api/v1/parking/{parkingId}/organization
     * TODO: GET /api/v1/parking/{parkingId}/vehicles
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

}
