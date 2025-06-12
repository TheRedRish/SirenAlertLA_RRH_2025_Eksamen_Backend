package com.redrish.sirenalertla_rrh_2025_eksamen.controller;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.DTO.SirenRequestDTO;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.service.SirenService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/sirens")
public class SirenRestController {

    private final SirenService sirenService;

    public SirenRestController(SirenService sirenService) {
        this.sirenService = sirenService;
    }

    @GetMapping
    public ResponseEntity<List<Siren>> getAll() {
        List<Siren> sirens = sirenService.getAll();
        return ResponseEntity.ok(sirens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Siren> get(@PathVariable int id) {
        try {
            Siren siren = sirenService.getById(id);
            return ResponseEntity.ok(siren);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Siren> create(@RequestBody Siren siren) {
        Siren created = sirenService.create(siren);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Siren> update(@PathVariable int id, @RequestBody SirenRequestDTO updated) {
        try {
            Siren sirenToUpdate = updated.toSiren();
            Siren updatedSiren = sirenService.update(id, sirenToUpdate);
            return ResponseEntity.ok(updatedSiren);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            sirenService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
