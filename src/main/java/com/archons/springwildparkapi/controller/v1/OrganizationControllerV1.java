package com.archons.springwildparkapi.controller.v1;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<OrganizationEntity>> getAllOrganizations(@RequestParam int requesterId) {
        try {
            return ResponseEntity.ok(organizationService.getAllOrganizations(requesterId));
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Optional<OrganizationEntity>> addOrganization(@RequestBody AddOrganizationRequest request) {
        try {
            return ResponseEntity.ok(organizationService.addOrganization(request));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (DuplicateEntityException ex) {
            return ResponseEntity.status(409).build();
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Optional<OrganizationEntity>> getOrganizationById(@RequestParam int requesterId,
            @PathVariable int organizationId) {
        try {
            return ResponseEntity.ok(organizationService.getOrganizationById(requesterId, organizationId));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<Optional<OrganizationEntity>> updateOrganization(
            @RequestBody UpdateOrganizationRequest request) {
        try {
            return ResponseEntity.ok(organizationService.updateOrganization(request));
        } catch (InsufficientPrivilegesException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (OrganizationNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IncompleteRequestException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@RequestParam int requesterId,
            @PathVariable int organizationId) {
        try {
            organizationService.deleteOrganization(requesterId, organizationId);
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
