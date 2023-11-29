package com.archons.springwildparkapi.controller.v1;

import java.util.List;

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

import com.archons.springwildparkapi.dto.AddOrganizationRequest;
import com.archons.springwildparkapi.dto.UpdateOrganizationRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.DuplicateEntityException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.service.OrganizationService;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationControllerV1 {
    /*
     * This controller class handles all organization related requests
     * 
     * Endpoints:
     * POST /api/v1/organizations/
     * GET /api/v1/organizations/{organizationId}
     * PUT /api/v1/organizations/{organizationId}
     * DELETE /api/v1/organizations/{organizationId}
     * 
     */
    private OrganizationService organizationService;

    @Autowired
    public OrganizationControllerV1(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrganizationEntity>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @PostMapping("/")
    public ResponseEntity<?> addOrganization(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody AddOrganizationRequest request) {
        try {
            return ResponseEntity.ok(organizationService.addOrganization(authorization, request));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (DuplicateEntityException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Organization already exists");
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete organization in request");
        }
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<?> getOrganizationById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable int organizationId) {
        try {
            return ResponseEntity.ok(organizationService.getOrganizationById(authorization, organizationId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<?> updateOrganization(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateOrganizationRequest request, @PathVariable int organizationId) {
        try {
            return ResponseEntity.ok(organizationService.updateOrganization(authorization, request, organizationId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized action");
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incomplete organization in request");
        }
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int organizationId) {
        try {
            organizationService.deleteOrganization(authorization, organizationId);
            return ResponseEntity.ok().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
