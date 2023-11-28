package com.archons.springwildparkapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.exceptions.InsufficientPrivilegesException;
import com.archons.springwildparkapi.exceptions.ParkingAreaNotFoundException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.ParkingAreaRepository;

@Service
public class ParkingAreaService {
    private final ParkingAreaRepository parkingAreaRepository;
    private final OrganizationService organizationService;

    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, OrganizationService organizationService) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.organizationService = organizationService;
    }

    public Optional<ParkingAreaEntity> getParkingAreaById(AccountEntity requester, int parkingAreaid)
            throws ParkingAreaNotFoundException, InsufficientPrivilegesException {
        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(parkingAreaid);

        if (!existingParkingArea.isPresent()) {
            throw new ParkingAreaNotFoundException();
        }

        ParkingAreaEntity parkingArea = existingParkingArea.get();

        if (!organizationService.isOrganizationMember(parkingArea.getOrganization(), requester)
                && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(parkingArea);
    }

    public Optional<ParkingAreaEntity> updateParkingArea(AccountEntity requester, int parkingAreaId,
            ParkingAreaEntity updatedParkingArea) throws InsufficientPrivilegesException, ParkingAreaNotFoundException {
        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(parkingAreaId);

        if (!existingParkingArea.isPresent()) {
            throw new ParkingAreaNotFoundException();
        }

        ParkingAreaEntity parkingArea = existingParkingArea.get();

        if (!organizationService.isOrganizationOwner(parkingArea.getOrganization(), requester) &&
                !organizationService.isOrganizationAdmin(parkingArea.getOrganization(), requester) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(parkingAreaRepository.save(updatedParkingArea));
    }

    public boolean deleteParkingArea(AccountEntity requester, int parkingId)
            throws InsufficientPrivilegesException, ParkingAreaNotFoundException {
        Optional<ParkingAreaEntity> existingParkingArea = getParkingAreaById(requester, parkingId);

        if (existingParkingArea.isPresent()) {
            throw new ParkingAreaNotFoundException();
        }

        ParkingAreaEntity parkingArea = existingParkingArea.get();

        if (!organizationService.isOrganizationOwner(parkingArea.getOrganization(), requester) &&
                !organizationService.isOrganizationAdmin(parkingArea.getOrganization(), requester) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        parkingAreaRepository.delete(parkingArea);
        return true;
    }

    public Optional<ParkingAreaEntity> addParkingArea(AccountEntity requester, ParkingAreaEntity parkingArea)
            throws InsufficientPrivilegesException {
        if (!organizationService.isOrganizationOwner(parkingArea.getOrganization(), requester) &&
                !organizationService.isOrganizationAdmin(parkingArea.getOrganization(), requester) &&
                requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivilegesException();
        }

        return Optional.of(parkingAreaRepository.save(parkingArea));
    }
}
