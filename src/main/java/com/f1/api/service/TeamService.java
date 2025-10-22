package com.f1.api.service;

import com.f1.api.entity.Team;
import com.f1.api.exception.ResourceAlreadyExistsException;
import com.f1.api.exception.ResourceNotFoundException;
import com.f1.api.generated.model.TeamRequest;
import com.f1.api.generated.model.TeamResponse;
import com.f1.api.mapper.TeamMapper;
import com.f1.api.repository.TeamRepository;
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
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public List<TeamResponse> getAllTeams() {
        log.info("Fetching all teams");
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TeamResponse getTeamById(Long id) {
        log.info("Fetching team with id: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
        return teamMapper.toResponse(team);
    }

    @Transactional
    public TeamResponse createTeam(TeamRequest request) {
        log.info("Creating new team: {}", request.getName());

        if (teamRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Team", "name", request.getName());
        }

        Team team = teamMapper.toEntity(request);
        Team savedTeam = teamRepository.save(team);
        log.info("Team created successfully with id: {}", savedTeam.getId());

        return teamMapper.toResponse(savedTeam);
    }

    @Transactional
    public TeamResponse updateTeam(Long id, TeamRequest request) {
        log.info("Updating team with id: {}", id);

        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        // Check if the new name conflicts with another team
        if (!existingTeam.getName().equals(request.getName()) &&
                teamRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Team", "name", request.getName());
        }

        teamMapper.updateEntity(existingTeam, request);
        Team updatedTeam = teamRepository.save(existingTeam);
        log.info("Team updated successfully with id: {}", updatedTeam.getId());

        return teamMapper.toResponse(updatedTeam);
    }

    @Transactional
    public void deleteTeam(Long id) {
        log.info("Deleting team with id: {}", id);

        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team", "id", id);
        }

        teamRepository.deleteById(id);
        log.info("Team deleted successfully with id: {}", id);
    }

    public Team getTeamEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
    }
}
