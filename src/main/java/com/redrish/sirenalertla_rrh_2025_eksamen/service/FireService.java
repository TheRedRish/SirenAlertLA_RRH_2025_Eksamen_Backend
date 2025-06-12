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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FireService {
    private final FireRepository fireRepository;
    private final SirenRepository sirenRepository;

    public FireService(FireRepository fireRepository, SirenRepository sirenRepository) {
        this.fireRepository = fireRepository;
        this.sirenRepository = sirenRepository;
    }

    public Fire getFireById(int id) {
        return fireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fire not found"));
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

    public Fire updateFire(int id, Fire updatedFire) {
        Fire fire = fireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fire not found"));

        Location newLocation = updatedFire.getLocation();

        List<Siren> oldSirens = fire.getSirens();
        List<Siren> newSirens = sirenRepository.findByIsActiveTrue().stream()
                .filter(s -> isWithin10Km(s.getLocation(), newLocation))
                .toList();

        Set<Siren> toRemove = new HashSet<>(oldSirens);
        toRemove.removeAll(newSirens); // sirens no longer within range

        Set<Siren> toAdd = new HashSet<>(newSirens);
        toAdd.removeAll(oldSirens); // newly affected sirens to add to fire

        // Update statuses for removed sirens if not part of another active fire
        List<Fire> activeFires = fireRepository.findByEndTimeIsNull().stream()
                .filter(f -> f.getId() != fire.getId())
                .toList();

        for (Siren s : toRemove) {
            boolean usedByOtherFires = activeFires.stream()
                    .anyMatch(f -> f.getSirens().contains(s));

            if (!usedByOtherFires) {
                s.setStatus(SirenStatus.SAFE);
                sirenRepository.save(s);
            }
        }

        // Update statuses for newly added sirens
        for (Siren s : toAdd) {
            s.setStatus(SirenStatus.EMERGENCY);
            sirenRepository.save(s);
        }

        // Finalize update on fire object
        fire.setLocation(newLocation);
        fire.setSirens(new ArrayList<>(newSirens));
        fire.setStartTime(updatedFire.getStartTime());
        fire.setEndTime(updatedFire.getEndTime());

        return fireRepository.save(fire);
    }

    public void delete(int id) {
        fireRepository.findById(id).orElseThrow();
        closeFire(id); // Close fire before deleting to update sirens
        fireRepository.deleteById(id);
    }

    private boolean isWithin10Km(Location a, Location b) {
        return LocationUtil.calculateDistanceKM(a, b) <= 10;
    }
}
