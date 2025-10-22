package com.f1.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams", uniqueConstraints = {
        @UniqueConstraint(name = "uk_team_name", columnNames = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private List<Driver> drivers = new ArrayList<>();

    @Version
    private Long version;

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
