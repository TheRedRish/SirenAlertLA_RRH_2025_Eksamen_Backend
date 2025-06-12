package com.redrish.sirenalertla_rrh_2025_eksamen.service;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.SirenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SirenService {

    private final SirenRepository sirenRepository;

    public SirenService(SirenRepository sirenRepository) {
        this.sirenRepository = sirenRepository;
    }

    public List<Siren> getAll() {
        return sirenRepository.findAll();
    }

    public Siren create(Siren siren) {
        return sirenRepository.save(siren);
    }

    public Siren update(int id, Siren updated) {
        Siren found = sirenRepository.findById(id).orElseThrow();
        found.setLocation(updated.getLocation());
        found.setStatus(updated.getStatus());
        found.setActive(updated.isActive());
        return sirenRepository.save(found);
    }

    public void delete(int id) {
        sirenRepository.findById(id).orElseThrow();
        sirenRepository.deleteById(id);
    }
}
