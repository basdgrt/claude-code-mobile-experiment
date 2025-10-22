package com.f1.api.controller;

import com.f1.api.generated.api.DriversApi;
import com.f1.api.generated.model.DriverRequest;
import com.f1.api.generated.model.DriverResponse;
import com.f1.api.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DriverController implements DriversApi {

    private final DriverService driverService;

    @Override
    public ResponseEntity<List<DriverResponse>> getAllDrivers(Long teamId) {
        log.info("GET /api/v1/drivers - Fetching all drivers" + (teamId != null ? " for team: " + teamId : ""));
        List<DriverResponse> drivers = driverService.getAllDrivers(teamId);
        return ResponseEntity.ok(drivers);
    }

    @Override
    public ResponseEntity<DriverResponse> getDriverById(Long driverId) {
        log.info("GET /api/v1/drivers/{} - Fetching driver by id", driverId);
        DriverResponse driver = driverService.getDriverById(driverId);
        return ResponseEntity.ok(driver);
    }

    @Override
    public ResponseEntity<DriverResponse> createDriver(DriverRequest driverRequest) {
        log.info("POST /api/v1/drivers - Creating new driver");
        DriverResponse driver = driverService.createDriver(driverRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @Override
    public ResponseEntity<DriverResponse> updateDriver(Long driverId, DriverRequest driverRequest) {
        log.info("PUT /api/v1/drivers/{} - Updating driver", driverId);
        DriverResponse driver = driverService.updateDriver(driverId, driverRequest);
        return ResponseEntity.ok(driver);
    }

    @Override
    public ResponseEntity<Void> deleteDriver(Long driverId) {
        log.info("DELETE /api/v1/drivers/{} - Deleting driver", driverId);
        driverService.deleteDriver(driverId);
        return ResponseEntity.noContent().build();
    }
}
