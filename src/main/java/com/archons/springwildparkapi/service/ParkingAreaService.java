package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.requests.AddParkingAreaRequest;
import com.archons.springwildparkapi.dto.requests.UpdateParkingAreaRequest;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.repository.ParkingAreaRepository;

@Service
public class ParkingAreaService extends BaseService {
    private final ParkingAreaRepository parkingAreaRepository;
    private final OrganizationService organizationService;
    private final AccountService accountService;

    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, OrganizationService organizationService,
            AccountService accountService) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.organizationService = organizationService;
        this.accountService = accountService;
    }

    public List<ParkingAreaEntity> getAllParkingAreas() {
        Iterable<ParkingAreaEntity> iterable = parkingAreaRepository.findAll();
        List<ParkingAreaEntity> parkingList = new ArrayList<>();

        for (ParkingAreaEntity p : iterable) {
            if (!p.isDeleted()) {
                parkingList.add(p);
            }
        }

        return parkingList;
    }

    public List<ParkingAreaEntity> getAllParkingAreas(String authorization) throws Exception {
        AccountEntity requester = accountService.getAccountFromToken(authorization);

        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        Iterable<ParkingAreaEntity> iterable = parkingAreaRepository.findAll();
        List<ParkingAreaEntity> parkingAreas = new ArrayList<>();
        iterable.forEach(parkingAreas::add);

        return parkingAreas;
    }

    public ParkingAreaEntity getParkingAreaById(int parkingAreaid) throws Exception {
        return parkingAreaRepository.findById(parkingAreaid)
                .orElseThrow(() -> new ParkingAreaNotFoundException());
    }

    public ParkingAreaEntity updateParkingArea(String authorization, UpdateParkingAreaRequest request,
            int parkingId) throws Exception {
        // Retreive entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        ParkingAreaEntity parkingArea = getParkingAreaById(parkingId);
        OrganizationEntity organization = parkingArea.getOrganization();
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Updateable fields
        if (request.getSlots() != 0) {
            parkingArea.setSlots(request.getSlots());
        }
        if (request.isDeleted() != parkingArea.isDeleted()) {
            parkingArea.setDeleted(request.isDeleted());
        }

        return parkingAreaRepository.save(parkingArea);
    }

    public void deleteParkingArea(String authorization, int parkingId) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        ParkingAreaEntity parkingArea = getParkingAreaById(parkingId);
        OrganizationEntity organization = parkingArea.getOrganization();
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Set deleted
        parkingArea.setDeleted(true);
        parkingAreaRepository.save(parkingArea);
    }

    public ParkingAreaEntity addParkingArea(String authorization, AddParkingAreaRequest request) throws Exception {
        // Retrieve entities
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        OrganizationEntity organization = organizationService.getOrganizationById(request.getOrganizationId());
        // Check permissions
        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }
        // Build entitiy
        ParkingAreaEntity newParkingArea = new ParkingAreaEntity();
        newParkingArea.setOrganization(organization);
        newParkingArea.setSlots(request.getSlots());

        return parkingAreaRepository.save(newParkingArea);
    }

    public List<ParkingAreaEntity> getOrganizationParking(int organizationId) throws Exception {
        OrganizationEntity organization = organizationService.getOrganizationById(organizationId);
        List<ParkingAreaEntity> iterable = getAllParkingAreas();
        List<ParkingAreaEntity> orgParking = new ArrayList<>();

        for (ParkingAreaEntity p : iterable) {
            if (organization.equals(p.getOrganization()) && !p.isDeleted()) {
                orgParking.add(p);
            }
        }

        return orgParking;
    }
}
