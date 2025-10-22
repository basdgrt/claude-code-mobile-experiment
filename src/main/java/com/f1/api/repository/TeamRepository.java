package com.f1.api.repository;

import com.f1.api.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Find a team by its name.
     *
     * @param name the team name
     * @return an Optional containing the team if found
     */
    Optional<Team> findByName(String name);

    /**
     * Check if a team exists with the given name.
     *
     * @param name the team name
     * @return true if a team exists with the name
     */
    boolean existsByName(String name);
}
