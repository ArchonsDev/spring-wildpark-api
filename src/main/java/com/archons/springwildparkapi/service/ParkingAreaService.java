package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.OrganizationEntity;
import com.archons.springwildparkapi.model.ParkingAreaEntity;
import com.archons.springwildparkapi.repository.ParkingAreaRepository;

@Service
public class ParkingAreaService {
    private final ParkingAreaRepository parkingAreaRepository;

    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
    }

    public List<ParkingAreaEntity> getParkingAreaByOrganization(OrganizationEntity organization) {
        Iterable<ParkingAreaEntity> iterable = parkingAreaRepository.findAll();
        List<ParkingAreaEntity> parkingAreaList = new ArrayList<>();

        for (ParkingAreaEntity parkingArea : iterable) {
            if (parkingArea.getOrganization().equals(organization)) {
                parkingAreaList.add(parkingArea);
            }
        }

        return parkingAreaList;
    }

    public Optional<ParkingAreaEntity> getParkingAreaById(int parkingAreaid) {
        return parkingAreaRepository.findById(parkingAreaid);
    }

    public Optional<ParkingAreaEntity> updateParkingArea(ParkingAreaEntity updatedParkingArea) {
        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(updatedParkingArea.getId());

        if (existingParkingArea.isPresent()) {
            return Optional.of(parkingAreaRepository.save(updatedParkingArea));
        }

        return Optional.ofNullable(null);
    }

    public boolean deleteParkingArea(int parkingAreaid) {
        Optional<ParkingAreaEntity> existingParkingArea = parkingAreaRepository.findById(parkingAreaid);

        if (existingParkingArea.isPresent()) {
            parkingAreaRepository.delete(existingParkingArea.get());
            return true;
        }

        return false;
    }
}
