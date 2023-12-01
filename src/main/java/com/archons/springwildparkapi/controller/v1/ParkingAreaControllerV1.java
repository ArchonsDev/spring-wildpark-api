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

import com.archons.springwildparkapi.dto.requests.AddParkingAreaRequest;
import com.archons.springwildparkapi.dto.requests.UpdateParkingAreaRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
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

    public ParkingAreaControllerV1(ParkingAreaService parkingAreaService) {
        this.parkingAreaService = parkingAreaService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllParkingAreas(@RequestHeader(name = "Authorization") String authorization)
            throws AccountNotFoundException, InsufficientPrivilegesException {
        return ResponseEntity.ok(parkingAreaService.getAllParkingAreas(authorization));
    }

    @PostMapping("/")
    public ResponseEntity<?> addParkingArea(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody AddParkingAreaRequest request)
            throws InsufficientPrivilegesException, AccountNotFoundException, OrganizationNotFoundException {
        return ResponseEntity.ok(parkingAreaService.addParkingArea(authorization, request));
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<?> getParkingAreaById(@PathVariable int parkingId)
            throws AccountNotFoundException, ParkingAreaNotFoundException, InsufficientPrivilegesException {
        return ResponseEntity.ok(parkingAreaService.getParkingAreaById(parkingId));
    }

    @PutMapping("/{parkingId}")
    public ResponseEntity<?> updateParkingArea(
            @RequestHeader(name = "Authorization") String authorization, @RequestBody UpdateParkingAreaRequest request,
            @PathVariable int parkingId)
            throws AccountNotFoundException, InsufficientPrivilegesException, ParkingAreaNotFoundException {
        return ResponseEntity.ok(parkingAreaService.updateParkingArea(authorization, request, parkingId));
    }

    @DeleteMapping("/{parkingId}")
    public ResponseEntity<?> deleteParkingArea(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int parkingId)
            throws AccountNotFoundException, InsufficientPrivilegesException, ParkingAreaNotFoundException {
        parkingAreaService.deleteParkingArea(authorization, parkingId);
        return ResponseEntity.ok().build();
    }
}