package com.archons.springwildparkapi.controller.v1;

import java.util.List;

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

import com.archons.springwildparkapi.dto.requests.AddOrganizationRequest;
import com.archons.springwildparkapi.dto.requests.UpdateOrganizationRequest;
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
            @RequestBody AddOrganizationRequest request)
            throws InsufficientPrivilegesException, AccountNotFoundException, DuplicateEntityException,
            IncompleteRequestException {
        return ResponseEntity.ok(organizationService.addOrganization(authorization, request));
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable int organizationId)
            throws OrganizationNotFoundException {
        return ResponseEntity.ok(organizationService.getOrganizationById(organizationId));
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<?> updateOrganization(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateOrganizationRequest request, @PathVariable int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException, AccountNotFoundException,
            IncompleteRequestException {
        return ResponseEntity.ok(organizationService.updateOrganization(authorization, request, organizationId));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int organizationId)
            throws InsufficientPrivilegesException, OrganizationNotFoundException, AccountNotFoundException {
        organizationService.deleteOrganization(authorization, organizationId);
        return ResponseEntity.ok().build();
    }
}
