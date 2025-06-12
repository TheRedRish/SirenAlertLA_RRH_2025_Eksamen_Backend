package com.redrish.sirenalertla_rrh_2025_eksamen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String name;

    @ManyToMany(mappedBy = "sirens")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Fire> fire;

    public Siren(Location location, SirenStatus status, boolean isActive, String name) {
        this.location = location;
        Status = status;
        this.isActive = isActive;
        this.name = name;
    }
}
