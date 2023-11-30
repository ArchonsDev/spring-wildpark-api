package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AddParkingAreaRequest;
import com.archons.springwildparkapi.dto.UpdateParkingAreaRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.OrganizationNotFoundException;
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

    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, OrganizationService organizationService,
            AccountService accountService) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.organizationService = organizationService;
        this.accountService = accountService;
    }

    public List<ParkingAreaEntity> getAllParkingAreas(String authorization)
            throws AccountNotFoundException, InsufficientPrivilegesException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);

        if (!isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        Iterable<ParkingAreaEntity> iterable = parkingAreaRepository.findAll();
        List<ParkingAreaEntity> parkingAreas = new ArrayList<>();
        iterable.forEach(parkingAreas::add);

        return parkingAreas;
    }

    public ParkingAreaEntity getParkingAreaById(String authorization, int parkingAreaid)
            throws AccountNotFoundException, ParkingAreaNotFoundException, InsufficientPrivilegesException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);

        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(parkingAreaid);

        if (!existingParkingArea.isPresent()) {
            throw new ParkingAreaNotFoundException();
        }

        ParkingAreaEntity parkingArea = existingParkingArea.get();

        if (!isOrganizationMember(parkingArea.getOrganization(), requester) && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        return parkingArea;
    }

    public ParkingAreaEntity updateParkingArea(String authorization, UpdateParkingAreaRequest request,
            int parkingId)
            throws AccountNotFoundException, InsufficientPrivilegesException, ParkingAreaNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        ParkingAreaEntity parkingArea = getParkingAreaById(authorization, parkingId);
        OrganizationEntity organization = parkingArea.getOrganization();

        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        if (request.getSlots() != 0) {
            parkingArea.setSlots(request.getSlots());
        }

        return parkingAreaRepository.save(parkingArea);
    }

    public void deleteParkingArea(String authorization, int parkingId)
            throws AccountNotFoundException, InsufficientPrivilegesException, ParkingAreaNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        ParkingAreaEntity parkingArea = getParkingAreaById(authorization, parkingId);
        OrganizationEntity organization = parkingArea.getOrganization();

        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)
                && !isAccountAdmin(requester)) {
            throw new InsufficientPrivilegesException();
        }

        parkingArea.setDeleted(true);
    }

    public ParkingAreaEntity addParkingArea(String authorization, AddParkingAreaRequest request)
            throws InsufficientPrivilegesException, AccountNotFoundException, OrganizationNotFoundException {
        AccountEntity requester = accountService.getAccountFromToken(authorization);
        OrganizationEntity organization = organizationService.getOrganizationById(request.getOrganizationId());

        if (!isOrganizationOwner(organization, requester) && !isOrganizationAdmin(organization, requester)) {
            throw new InsufficientPrivilegesException();
        }

        ParkingAreaEntity newParkingArea = new ParkingAreaEntity();
        newParkingArea.setOrganization(organization);
        newParkingArea.setSlots(request.getSlots());

        return parkingAreaRepository.save(newParkingArea);
    }
}
