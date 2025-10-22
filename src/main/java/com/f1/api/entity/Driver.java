package com.f1.api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "drivers", uniqueConstraints = {
        @UniqueConstraint(name = "uk_driver_number", columnNames = "driver_number")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "driver_number", nullable = false, unique = true)
    private Integer driverNumber;

    @Column(nullable = false, length = 50)
    private String nationality;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(name = "fk_driver_team"))
    private Team team;

    @Version
    private Long version;

    public Driver() {
    }

    public Driver(Long id, String firstName, String lastName, Integer driverNumber,
                  String nationality, LocalDate dateOfBirth, Team team, Long version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.driverNumber = driverNumber;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.team = team;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", driverNumber=" + driverNumber +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", teamId=" + (team != null ? team.getId() : null) +
                '}';
    }
}
