package com.redrish.sirenalertla_rrh_2025_eksamen.service;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Location;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.SirenStatus;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.FireRepository;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.SirenRepository;
import com.redrish.sirenalertla_rrh_2025_eksamen.util.LocationUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FireService {
    private final FireRepository fireRepository;
    private final SirenRepository sirenRepository;

    public FireService(FireRepository fireRepository, SirenRepository sirenRepository) {
        this.fireRepository = fireRepository;
        this.sirenRepository = sirenRepository;
    }

    public Fire createFire(double latitude, double longitude) {
        Location location = new Location(latitude, longitude);
        List<Siren> nearbySirens = sirenRepository.findByIsActiveTrue().stream()
                .filter(s -> isWithin10Km(s.getLocation(), location))
                .toList();

        nearbySirens.forEach(s -> s.setStatus(SirenStatus.EMERGENCY));
        sirenRepository.saveAll(nearbySirens);

        Fire fire = new Fire();
        fire.setLocation(location);
        fire.setStartTime(LocalDateTime.now());
        fire.setSirens(nearbySirens);

        return fireRepository.save(fire);
    }

    public List<Fire> getActiveFires() {
        return fireRepository.findByEndTimeIsNull();
    }

    public List<Fire> getAllFires() {
        return fireRepository.findAll();
    }

    public Fire closeFire(int id) {
        Fire fire = fireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fire not found"));

        fire.setEndTime(LocalDateTime.now());

        List<Siren> affectedSirens = fire.getSirens();
        for (Siren siren : affectedSirens) {
            // Find alle aktive fires hvor denne siren også er tilknyttet
            boolean isSirenInOtherActiveEmergencies = fireRepository.findByEndTimeIsNull().stream()
                    .filter(f -> f.getId() != fire.getId())
                    .anyMatch(e -> e.getSirens().contains(siren));

            if (!isSirenInOtherActiveEmergencies) {
                siren.setStatus(SirenStatus.SAFE);
                sirenRepository.save(siren);
            }
        }

        return fireRepository.save(fire);
    }

    private boolean isWithin10Km(Location a, Location b) {
        return LocationUtil.calculateDistanceKM(a, b) <= 10;
    }
}
