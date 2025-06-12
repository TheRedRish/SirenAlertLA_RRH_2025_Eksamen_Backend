package com.redrish.sirenalertla_rrh_2025_eksamen.controller;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.DTO.FireRequestDTO;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import com.redrish.sirenalertla_rrh_2025_eksamen.service.FireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/fires")
public class FireRestController {

    private final FireService fireService;

    public FireRestController(FireService fireService) {
        this.fireService = fireService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fire> getFireById(@PathVariable int id) {
        try {
            Fire fire = fireService.getFireById(id);
            return ResponseEntity.ok(fire);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Fire> createFire(@RequestParam double latitude, @RequestParam double longitude) {
        Fire createdFire = fireService.createFire(latitude, longitude);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFire);
    }

    @GetMapping
    public ResponseEntity<List<Fire>> getFires(@RequestParam(required = false) String status) {
        List<Fire> fires;
        if ("active".equalsIgnoreCase(status)) {
            fires = fireService.getActiveFires();
        } else {
            fires = fireService.getAllFires();
        }
        return ResponseEntity.ok(fires);
    }

    @PutMapping("/{id}/closure")
    public ResponseEntity<Fire> closeFire(@PathVariable int id) {
        try {
            Fire closedFire = fireService.closeFire(id);
            return ResponseEntity.ok(closedFire);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fire> updateFire(@PathVariable int id, @RequestBody FireRequestDTO updated) {
        try {
            Fire fireToUpdate = updated.toFire();
            Fire updatedFire = fireService.updateFire(id, fireToUpdate);
            return ResponseEntity.ok(updatedFire);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            fireService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}