package com.redrish.sirenalertla_rrh_2025_eksamen.service;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.SirenStatus;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.FireRepository;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.SirenRepository;
import com.redrish.sirenalertla_rrh_2025_eksamen.util.LocationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SirenService {

    private final SirenRepository sirenRepository;
    private final FireRepository fireRepository;

    public SirenService(SirenRepository sirenRepository, FireRepository fireRepository) {
        this.fireRepository = fireRepository;
        this.sirenRepository = sirenRepository;
    }

    public List<Siren> getAll() {
        return sirenRepository.findAll();
    }

    public Siren getById(int id) {
        return sirenRepository.findById(id).orElseThrow();
    }

    public Siren create(Siren siren) {
        return sirenRepository.save(siren);
    }

    public Siren update(int id, Siren updated) {
        Siren siren = sirenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Siren not found"));

        // Update the fields
        siren.setLocation(updated.getLocation());
        siren.setActive(updated.isActive());
        siren.setName(updated.getName());

        // Update the status and related fires
        List<Fire> activeFires = fireRepository.findByEndTimeIsNull();
        List<Fire> firesNowInRange = activeFires.stream()
                .filter(fire -> LocationUtil.calculateDistanceKM(fire.getLocation(), updated.getLocation()) <= 10)
                .toList();

        // Remove siren from fires it is no longer in range of
        List<Fire> previouslyLinkedFires = siren.getFire();
        for (Fire fire : previouslyLinkedFires) {
            if (!firesNowInRange.contains(fire)) {
                fire.getSirens().remove(siren);
                siren.getFire().remove(fire);
            }
        }

        // Add siren to new fires it's now in range of
        for (Fire fire : firesNowInRange) {
            if (!fire.getSirens().contains(siren)) {
                fire.getSirens().add(siren);
                siren.getFire().add(fire);
            }
        }

        // Update siren status
        siren.setStatus(firesNowInRange.isEmpty() ? SirenStatus.SAFE : SirenStatus.EMERGENCY);

        return sirenRepository.save(siren);
    }

    public void delete(int id) {
        sirenRepository.findById(id).orElseThrow();
        sirenRepository.deleteById(id);
    }
}
