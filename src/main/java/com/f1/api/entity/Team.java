package com.f1.api.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams", uniqueConstraints = {
        @UniqueConstraint(name = "uk_team_name", columnNames = "name")
})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String base;

    @Column(name = "team_chief", nullable = false, length = 100)
    private String teamChief;

    @Column(name = "power_unit", nullable = false, length = 50)
    private String powerUnit;

    @Column(name = "first_entry")
    private Integer firstEntry;

    @Column
    private Integer championships;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Driver> drivers = new ArrayList<>();

    @Version
    private Long version;

    public Team() {
    }

    public Team(Long id, String name, String base, String teamChief, String powerUnit,
                Integer firstEntry, Integer championships, List<Driver> drivers, Long version) {
        this.id = id;
        this.name = name;
        this.base = base;
        this.teamChief = teamChief;
        this.powerUnit = powerUnit;
        this.firstEntry = firstEntry;
        this.championships = championships;
        this.drivers = drivers != null ? drivers : new ArrayList<>();
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getTeamChief() {
        return teamChief;
    }

    public void setTeamChief(String teamChief) {
        this.teamChief = teamChief;
    }

    public String getPowerUnit() {
        return powerUnit;
    }

    public void setPowerUnit(String powerUnit) {
        this.powerUnit = powerUnit;
    }

    public Integer getFirstEntry() {
        return firstEntry;
    }

    public void setFirstEntry(Integer firstEntry) {
        this.firstEntry = firstEntry;
    }

    public Integer getChampionships() {
        return championships;
    }

    public void setChampionships(Integer championships) {
        this.championships = championships;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", base='" + base + '\'' +
                ", teamChief='" + teamChief + '\'' +
                ", powerUnit='" + powerUnit + '\'' +
                ", firstEntry=" + firstEntry +
                ", championships=" + championships +
                '}';
    }
}
