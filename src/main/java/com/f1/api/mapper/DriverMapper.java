package com.f1.api.mapper;

import com.f1.api.entity.Driver;
import com.f1.api.entity.Team;
import com.f1.api.generated.model.DriverRequest;
import com.f1.api.generated.model.DriverResponse;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public DriverResponse toResponse(Driver driver) {
        if (driver == null) {
            return null;
        }

        DriverResponse response = new DriverResponse();
        response.setId(driver.getId());
        response.setFirstName(driver.getFirstName());
        response.setLastName(driver.getLastName());
        response.setDriverNumber(driver.getDriverNumber());
        response.setNationality(driver.getNationality());
        response.setDateOfBirth(driver.getDateOfBirth());

        if (driver.getTeam() != null) {
            response.setTeamId(driver.getTeam().getId());
            response.setTeamName(driver.getTeam().getName());
        }

        return response;
    }

    public Driver toEntity(DriverRequest request, Team team) {
        if (request == null) {
            return null;
        }

        return Driver.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .driverNumber(request.getDriverNumber())
                .nationality(request.getNationality())
                .dateOfBirth(request.getDateOfBirth())
                .team(team)
                .build();
    }

    public void updateEntity(Driver driver, DriverRequest request, Team team) {
        if (driver == null || request == null) {
            return;
        }

        driver.setFirstName(request.getFirstName());
        driver.setLastName(request.getLastName());
        driver.setDriverNumber(request.getDriverNumber());
        driver.setNationality(request.getNationality());
        driver.setDateOfBirth(request.getDateOfBirth());
        driver.setTeam(team);
    }
}
