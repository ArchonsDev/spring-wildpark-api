package com.archons.springwildparkapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.UpdateParkingAreaRequest;
import com.archons.springwildparkapi.exceptions.AccountNotFoundException;
import com.archons.springwildparkapi.exceptions.IncompleteRequestException;
import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.ParkingAreaRepository;

import jakarta.transaction.Transactional;

@Service
public class ParkingAreaService {
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

    @Transactional
    public ParkingAreaEntity addParkingArea(String authorization, ParkingAreaEntity newParkingArea)
            throws InsufficientPrivilegesException, AccountNotFoundException {

        AccountEntity requester = accountService.getAccountFromToken(authorization);

        // Checks if requester is the owner or admin
        if ((!organizationService.isOrganizationOwner(newParkingArea.getOrganization(), requester) &&
                !organizationService.isOrganizationAdmin(newParkingArea.getOrganization(), requester)) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return parkingAreaRepository.save(newParkingArea);
    }

    public ParkingAreaEntity getParkingAreaById(String authorization, int parkingAreaid)
            throws ParkingAreaNotFoundException, InsufficientPrivilegesException, AccountNotFoundException {

        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(parkingAreaid);

        if (!existingParkingArea.isPresent()) {
            throw new ParkingAreaNotFoundException();
        }

        ParkingAreaEntity parkingArea = existingParkingArea.get();

        if (!organizationService.isOrganizationMember(parkingArea.getOrganization(), requester)
                && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return parkingArea;
    }

    public ParkingAreaEntity updateParkingArea(String authorization, UpdateParkingAreaRequest request,
            int parkingAreaId) throws InsufficientPrivilegesException, ParkingAreaNotFoundException,
            AccountNotFoundException, IncompleteRequestException {

        AccountEntity requester = accountService.getAccountFromToken(authorization);
        ParkingAreaEntity updatedParkingArea = request.getUpdatedParkingArea();
        ParkingAreaEntity parkingArea = getParkingAreaById(authorization, parkingAreaId);

        // Checks first if requester has the privileges to update :)
        if ((!organizationService.isOrganizationOwner(parkingArea.getOrganization(), requester) &&
                !organizationService.isOrganizationAdmin(parkingArea.getOrganization(), requester)) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        if (updatedParkingArea.getId() == 0) {
            throw new IncompleteRequestException();
        }

        if (updatedParkingArea.getSlots() != 0) {
            parkingArea.setSlots(updatedParkingArea.getSlots());
        }

        if (updatedParkingArea.getParkedVehicles() != null) {
            parkingArea.setParkedVehicles(updatedParkingArea.getParkedVehicles());
        }

        if (updatedParkingArea.getOrganization() != null) {
            parkingArea.setOrganization(updatedParkingArea.getOrganization());
        }

        return parkingAreaRepository.save(parkingArea);
    }

    public void deleteParkingArea(String authorization, int parkingAreaId)
            throws InsufficientPrivilegesException, ParkingAreaNotFoundException, AccountNotFoundException {

        AccountEntity requester = accountService.getAccountFromToken(authorization);
        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(parkingAreaId);

        if (!existingParkingArea.isPresent()) {
            throw new ParkingAreaNotFoundException();
        }

        ParkingAreaEntity parkingArea = existingParkingArea.get();

        // Checks first if requester has the privileges to delete :)
        if ((!organizationService.isOrganizationOwner(parkingArea.getOrganization(), requester) &&
                !organizationService.isOrganizationAdmin(parkingArea.getOrganization(), requester)) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        parkingArea.setDeleted(true);
        parkingAreaRepository.save(parkingArea);
    }
}
