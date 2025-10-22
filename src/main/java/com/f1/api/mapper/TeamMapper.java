package com.f1.api.mapper;

import com.f1.api.entity.Team;
import com.f1.api.generated.model.TeamRequest;
import com.f1.api.generated.model.TeamResponse;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public TeamResponse toResponse(Team team) {
        if (team == null) {
            return null;
        }

        TeamResponse response = new TeamResponse();
        response.setId(team.getId());
        response.setName(team.getName());
        response.setBase(team.getBase());
        response.setTeamChief(team.getTeamChief());
        response.setPowerUnit(team.getPowerUnit());
        response.setFirstEntry(team.getFirstEntry());
        response.setChampionships(team.getChampionships());

        return response;
    }

    public Team toEntity(TeamRequest request) {
        if (request == null) {
            return null;
        }

        return Team.builder()
                .name(request.getName())
                .base(request.getBase())
                .teamChief(request.getTeamChief())
                .powerUnit(request.getPowerUnit())
                .firstEntry(request.getFirstEntry())
                .championships(request.getChampionships())
                .build();
    }

    public void updateEntity(Team team, TeamRequest request) {
        if (team == null || request == null) {
            return;
        }

        team.setName(request.getName());
        team.setBase(request.getBase());
        team.setTeamChief(request.getTeamChief());
        team.setPowerUnit(request.getPowerUnit());
        team.setFirstEntry(request.getFirstEntry());
        team.setChampionships(request.getChampionships());
    }
}
