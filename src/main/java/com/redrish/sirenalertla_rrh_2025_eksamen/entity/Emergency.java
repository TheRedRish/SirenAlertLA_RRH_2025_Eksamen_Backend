package com.redrish.sirenalertla_rrh_2025_eksamen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Location location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
            name = "emergency_siren",
            joinColumns = @JoinColumn(name = "emergency_id"),
            inverseJoinColumns = @JoinColumn(name = "siren_id")
    )
    private List<Siren> sirens;
}
