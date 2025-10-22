package com.f1.api.repository;

import com.f1.api.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    /**
     * Find drivers by team ID.
     *
     * @param teamId the team ID
     * @return a list of drivers for the team
     */
    @Query("SELECT d FROM Driver d JOIN FETCH d.team WHERE d.team.id = :teamId")
    List<Driver> findByTeamId(@Param("teamId") Long teamId);

    /**
     * Find all drivers with their teams eagerly loaded.
     *
     * @return a list of all drivers with teams
     */
    @Query("SELECT d FROM Driver d JOIN FETCH d.team")
    List<Driver> findAllWithTeam();

    /**
     * Find a driver by ID with team eagerly loaded.
     *
     * @param id the driver ID
     * @return an Optional containing the driver if found
     */
    @Query("SELECT d FROM Driver d JOIN FETCH d.team WHERE d.id = :id")
    Optional<Driver> findByIdWithTeam(@Param("id") Long id);

    /**
     * Find a driver by driver number.
     *
     * @param driverNumber the driver number
     * @return an Optional containing the driver if found
     */
    Optional<Driver> findByDriverNumber(Integer driverNumber);

    /**
     * Check if a driver exists with the given driver number.
     *
     * @param driverNumber the driver number
     * @return true if a driver exists with the number
     */
    boolean existsByDriverNumber(Integer driverNumber);

    /**
     * Check if a driver exists with the given driver number excluding a specific ID.
     *
     * @param driverNumber the driver number
     * @param id the driver ID to exclude
     * @return true if another driver exists with the number
     */
    boolean existsByDriverNumberAndIdNot(Integer driverNumber, Long id);
}
