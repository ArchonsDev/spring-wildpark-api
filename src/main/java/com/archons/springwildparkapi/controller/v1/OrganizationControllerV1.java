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

import com.archons.springwildparkapi.dto.reesponses.OrganizationMemberResponse;
import com.archons.springwildparkapi.dto.requests.AddOrganizationRequest;
import com.archons.springwildparkapi.dto.requests.UpdateOrganizationRequest;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.service.OrganizationService;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationControllerV1 {
    private OrganizationService organizationService;

    public OrganizationControllerV1(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrganizationEntity>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @PostMapping("/")
    public ResponseEntity<?> addOrganization(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody AddOrganizationRequest request) throws Exception {
        return ResponseEntity.ok(organizationService.addOrganization(authorization, request));
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable int organizationId) throws Exception {
        return ResponseEntity.ok(organizationService.getOrganizationById(organizationId));
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<?> updateOrganization(@RequestHeader(name = "Authorization") String authorization,
            @RequestBody UpdateOrganizationRequest request, @PathVariable int organizationId) throws Exception {
        return ResponseEntity.ok(organizationService.updateOrganization(authorization, request, organizationId));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int organizationId) throws Exception {
        organizationService.deleteOrganization(authorization, organizationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{organizationId}/members")
    public ResponseEntity<OrganizationMemberResponse> getOrganizationAccounts(
            @RequestHeader(name = "Authorization") String authorization, @PathVariable int organizationId)
            throws Exception {
        return ResponseEntity.ok(organizationService.getOrganizationAccounts(authorization, organizationId));
    }

    @PostMapping("/{organizationId}/members")
    public ResponseEntity<OrganizationMemberResponse> addOrganizationMember(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody int accountId, @PathVariable int organizationId) throws Exception {
        return ResponseEntity.ok(organizationService.addOrganizationMember(authorization, accountId, organizationId));
    }

    @PostMapping("/{organizationId}/admins")
    public ResponseEntity<OrganizationMemberResponse> addOrganizationAdmin(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody int accountId, @PathVariable int organizationId) throws Exception {
        return ResponseEntity.ok(organizationService.addOrganizationAdmin(authorization, accountId, organizationId));
    }

    @DeleteMapping("/{organizationId}/members/{accountId}")
    public ResponseEntity<Void> kickMember(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int organizationId, @PathVariable int accountId) throws Exception {
        organizationService.kickMember(authorization, accountId, organizationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{organizationId}/admins/{accountId}")
    public ResponseEntity<Void> demote(@RequestHeader(name = "Authorization") String authorization,
            @PathVariable int organizationId, @PathVariable int accountId) throws Exception {
        organizationService.demote(authorization, accountId, organizationId);
        return ResponseEntity.ok().build();
    }
}
