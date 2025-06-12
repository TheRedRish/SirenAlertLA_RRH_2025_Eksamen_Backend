package com.redrish.sirenalertla_rrh_2025_eksamen.controller;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import com.redrish.sirenalertla_rrh_2025_eksamen.service.FireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fires")
public class FireRestController {

    private final FireService fireService;

    public FireRestController(FireService fireService) {
        this.fireService = fireService;
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
}