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

        sirens.add(new Siren(new Location(34.0522, -118.2437), SirenStatus.SAFE, true));   // Downtown LA
        sirens.add(new Siren(new Location(34.1015, -118.3269), SirenStatus.SAFE, true));   // Hollywood
        sirens.add(new Siren(new Location(33.9850, -118.4695), SirenStatus.SAFE, true));   // Venice Beach
        sirens.add(new Siren(new Location(34.0736, -118.4004), SirenStatus.SAFE, true));   // Beverly Hills
        sirens.add(new Siren(new Location(34.0195, -118.4912), SirenStatus.SAFE, true));   // Santa Monica

        sirens.add(new Siren(new Location(34.1381, -118.3534), SirenStatus.SAFE, true));   // Burbank
        sirens.add(new Siren(new Location(34.1478, -118.1445), SirenStatus.SAFE, true));   // Pasadena
        sirens.add(new Siren(new Location(34.0310, -118.2673), SirenStatus.SAFE, false));   // Koreatown
        sirens.add(new Siren(new Location(34.0242, -118.4965), SirenStatus.SAFE, true));   // Marina del Rey
        sirens.add(new Siren(new Location(34.0407, -118.2468), SirenStatus.SAFE, true));   // Little Tokyo

        sirens.add(new Siren(new Location(34.0625, -118.3082), SirenStatus.SAFE, true));   // West Hollywood
        sirens.add(new Siren(new Location(33.9523, -118.3455), SirenStatus.SAFE, false));   // Torrance
        sirens.add(new Siren(new Location(34.1808, -118.3089), SirenStatus.SAFE, true));   // Glendale
        sirens.add(new Siren(new Location(34.1477, -118.1445), SirenStatus.SAFE, true));   // Pasadena (repeat, can replace)
        sirens.add(new Siren(new Location(34.0119, -118.4925), SirenStatus.SAFE, true));   // Venice (repeat, can replace)

        sirens.add(new Siren(new Location(33.9425, -118.4081), SirenStatus.SAFE, true));   // LAX Airport
        sirens.add(new Siren(new Location(34.0520, -118.2439), SirenStatus.SAFE, false));   // Downtown LA (nearby)
        sirens.add(new Siren(new Location(34.0739, -118.2400), SirenStatus.SAFE, true));   // Echo Park
        sirens.add(new Siren(new Location(34.1253, -118.3004), SirenStatus.SAFE, true));   // North Hollywood
        sirens.add(new Siren(new Location(34.0983, -118.3295), SirenStatus.SAFE, true));   // Hollywood Hills

        sirens.add(new Siren(new Location(34.0635, -118.4455), SirenStatus.SAFE, false));   // Century City
        sirens.add(new Siren(new Location(33.9803, -118.4517), SirenStatus.SAFE, false));   // Culver City
        sirens.add(new Siren(new Location(34.0316, -118.2671), SirenStatus.SAFE, true));   // Mid-Wilshire
        sirens.add(new Siren(new Location(34.0403, -118.2697), SirenStatus.SAFE, true));   // Chinatown
        sirens.add(new Siren(new Location(34.0734, -118.2400), SirenStatus.SAFE, true));   // Echo Park (repeat, can replace)

        sirenRepository.saveAll(sirens);
    }
}
