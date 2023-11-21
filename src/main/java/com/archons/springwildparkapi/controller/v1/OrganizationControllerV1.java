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
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.service.OrganizationService;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationControllerV1 {
    /*
     * This controller class handles all organization related requests
     * 
     * Endpoints:
     * ✓ TODO: POST /api/v1/organizations/
     * ✓ TODO: GET /api/v1/organizations/{organizationId}
     * TODO: PUT /api/v1/organizations/{organizationId}
     * TODO: DELETE /api/v1/organizations/{organizationId}
     * 
     */
    private OrganizationService organizationService;

    @Autowired
    public OrganizationControllerV1(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/")
    public ResponseEntity<Optional<OrganizationEntity>> addOrganization(@RequestBody AccountEntity requester,
            @RequestBody OrganizationEntity newOrganization) {
        try {
            return ResponseEntity.ok(organizationService.addOrganization(requester, newOrganization));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Optional<OrganizationEntity>> getOrganizationById(@RequestBody AccountEntity requester,
            @RequestParam int organizationId) {
        try {
            return ResponseEntity.ok(organizationService.getOrganizationById(requester, organizationId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<Optional<OrganizationEntity>> updateOrganization(
            @RequestBody AccountEntity requester,
            @RequestBody OrganizationEntity updatedOrganization,
            @RequestParam int organizationId) {

        try {
            return ResponseEntity
                    .ok(organizationService.updateOrganization(requester, updatedOrganization, organizationId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@RequestBody AccountEntity requester,
            @RequestParam int organizationId) {
        try {
            organizationService.deleteOrganization(requester, organizationId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OrganizationNotFoundException ex) {

            return ResponseEntity.notFound().build();
        }
    }

}
