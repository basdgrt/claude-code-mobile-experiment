package com.f1.api.service;

import com.f1.api.entity.Driver;
import com.f1.api.entity.Team;
import com.f1.api.exception.ResourceAlreadyExistsException;
import com.f1.api.exception.ResourceNotFoundException;
import com.f1.api.generated.model.DriverRequest;
import com.f1.api.generated.model.DriverResponse;
import com.f1.api.mapper.DriverMapper;
import com.f1.api.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final TeamService teamService;

    public List<DriverResponse> getAllDrivers(Long teamId) {
        log.info("Fetching all drivers" + (teamId != null ? " for team id: " + teamId : ""));

        List<Driver> drivers;
        if (teamId != null) {
            drivers = driverRepository.findByTeamId(teamId);
        } else {
            drivers = driverRepository.findAllWithTeam();
        }

        return drivers.stream()
                .map(driverMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DriverResponse getDriverById(Long id) {
        log.info("Fetching driver with id: {}", id);
        Driver driver = driverRepository.findByIdWithTeam(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
        return driverMapper.toResponse(driver);
    }

    public List<DriverResponse> getDriversByTeamId(Long teamId) {
        log.info("Fetching drivers for team id: {}", teamId);

        // Validate that the team exists
        teamService.getTeamEntityById(teamId);

        List<Driver> drivers = driverRepository.findByTeamId(teamId);
        return drivers.stream()
                .map(driverMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DriverResponse createDriver(DriverRequest request) {
        log.info("Creating new driver: {} {}", request.getFirstName(), request.getLastName());

        if (driverRepository.existsByDriverNumber(request.getDriverNumber())) {
            throw new ResourceAlreadyExistsException("Driver", "driver number", request.getDriverNumber());
        }

        Team team = teamService.getTeamEntityById(request.getTeamId());
        Driver driver = driverMapper.toEntity(request, team);
        Driver savedDriver = driverRepository.save(driver);
        log.info("Driver created successfully with id: {}", savedDriver.getId());

        return driverMapper.toResponse(savedDriver);
    }

    @Transactional
    public DriverResponse updateDriver(Long id, DriverRequest request) {
        log.info("Updating driver with id: {}", id);

        Driver existingDriver = driverRepository.findByIdWithTeam(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));

        // Check if the new driver number conflicts with another driver
        if (!existingDriver.getDriverNumber().equals(request.getDriverNumber()) &&
                driverRepository.existsByDriverNumberAndIdNot(request.getDriverNumber(), id)) {
            throw new ResourceAlreadyExistsException("Driver", "driver number", request.getDriverNumber());
        }

        Team team = teamService.getTeamEntityById(request.getTeamId());
        driverMapper.updateEntity(existingDriver, request, team);
        Driver updatedDriver = driverRepository.save(existingDriver);
        log.info("Driver updated successfully with id: {}", updatedDriver.getId());

        return driverMapper.toResponse(updatedDriver);
    }

    @Transactional
    public void deleteDriver(Long id) {
        log.info("Deleting driver with id: {}", id);

        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver", "id", id);
        }

        driverRepository.deleteById(id);
        log.info("Driver deleted successfully with id: {}", id);
    }
}
