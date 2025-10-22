package com.f1.api.controller;

import com.f1.api.generated.api.TeamsApi;
import com.f1.api.generated.model.DriverResponse;
import com.f1.api.generated.model.TeamRequest;
import com.f1.api.generated.model.TeamResponse;
import com.f1.api.service.DriverService;
import com.f1.api.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController implements TeamsApi {

    private final TeamService teamService;
    private final DriverService driverService;

    @Override
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        log.info("GET /api/v1/teams - Fetching all teams");
        List<TeamResponse> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @Override
    public ResponseEntity<TeamResponse> getTeamById(Long teamId) {
        log.info("GET /api/v1/teams/{} - Fetching team by id", teamId);
        TeamResponse team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(team);
    }

    @Override
    public ResponseEntity<TeamResponse> createTeam(TeamRequest teamRequest) {
        log.info("POST /api/v1/teams - Creating new team");
        TeamResponse team = teamService.createTeam(teamRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    @Override
    public ResponseEntity<TeamResponse> updateTeam(Long teamId, TeamRequest teamRequest) {
        log.info("PUT /api/v1/teams/{} - Updating team", teamId);
        TeamResponse team = teamService.updateTeam(teamId, teamRequest);
        return ResponseEntity.ok(team);
    }

    @Override
    public ResponseEntity<Void> deleteTeam(Long teamId) {
        log.info("DELETE /api/v1/teams/{} - Deleting team", teamId);
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<DriverResponse>> getDriversByTeam(Long teamId) {
        log.info("GET /api/v1/teams/{}/drivers - Fetching drivers for team", teamId);
        List<DriverResponse> drivers = driverService.getDriversByTeamId(teamId);
        return ResponseEntity.ok(drivers);
    }
}
