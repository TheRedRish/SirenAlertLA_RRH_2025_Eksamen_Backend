package com.redrish.sirenalertla_rrh_2025_eksamen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Siren {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private SirenStatus Status;
    private boolean isActive;

    @ManyToMany(mappedBy = "sirens")
    private List<Fire> fire;

    public Siren(Location location, SirenStatus status, boolean isActive) {
        this.location = location;
        this.Status = status;
        this.isActive = isActive;
    }
}
