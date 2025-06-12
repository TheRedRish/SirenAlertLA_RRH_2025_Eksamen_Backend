package com.redrish.sirenalertla_rrh_2025_eksamen.config;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Location;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.SirenStatus;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.SirenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final SirenRepository sirenRepository;

    public InitData(SirenRepository sirenRepository) {
        this.sirenRepository = sirenRepository;
    }

    @Override
    public void run(String... args) {
        List<Siren> sirens = new ArrayList<>();

        sirens.add(new Siren(new Location(34.009, -118.497), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.010, -118.496), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.011, -118.495), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.012, -118.494), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.013, -118.493), SirenStatus.SAFE, true));

        sirens.add(new Siren(new Location(34.014, -118.492), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.015, -118.491), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.016, -118.490), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.017, -118.489), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.018, -118.488), SirenStatus.SAFE, true));

        sirens.add(new Siren(new Location(34.048, -118.525), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.049, -118.524), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.050, -118.523), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.051, -118.522), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.052, -118.521), SirenStatus.SAFE, true));

        sirens.add(new Siren(new Location(34.053, -118.520), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.054, -118.519), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.055, -118.518), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.056, -118.517), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.057, -118.516), SirenStatus.SAFE, true));

        sirens.add(new Siren(new Location(34.030, -118.510), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.031, -118.509), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.032, -118.508), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.033, -118.507), SirenStatus.SAFE, true));
        sirens.add(new Siren(new Location(34.034, -118.506), SirenStatus.SAFE, true));

        sirenRepository.saveAll(sirens);
    }
}
