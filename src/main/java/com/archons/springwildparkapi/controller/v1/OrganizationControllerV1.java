package com.archons.springwildparkapi.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.service.OrganizationService;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationControllerV1 {
    /*
     * This controller class handles all organization related requests
     */
    private OrganizationService organizationService;

    @Autowired
    public OrganizationControllerV1(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}
