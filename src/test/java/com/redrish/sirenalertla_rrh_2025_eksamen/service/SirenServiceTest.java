package com.redrish.sirenalertla_rrh_2025_eksamen.service;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Location;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.SirenStatus;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.FireRepository;
import com.redrish.sirenalertla_rrh_2025_eksamen.repository.SirenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SirenServiceTest {

    @Mock
    private SirenRepository sirenRepository;

    @Mock
    private FireRepository fireRepository;

    @InjectMocks
    private SirenService sirenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Siren> sirens = new ArrayList<>();
        sirens.add(new Siren());
        when(sirenRepository.findAll()).thenReturn(sirens);

        List<Siren> result = sirenService.getAll();

        assertEquals(1, result.size());
        verify(sirenRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Siren siren = new Siren();
        when(sirenRepository.findById(1)).thenReturn(Optional.of(siren));

        Siren result = sirenService.getById(1);

        assertEquals(siren, result);
        verify(sirenRepository, times(1)).findById(1);
    }

    @Test
    void testCreate() {
        Siren siren = new Siren();
        when(sirenRepository.save(siren)).thenReturn(siren);

        Siren result = sirenService.create(siren);

        assertEquals(siren, result);
        verify(sirenRepository, times(1)).save(siren);
    }

    @Test
    void testDelete() {
        Siren siren = new Siren();
        when(sirenRepository.findById(1)).thenReturn(Optional.of(siren));

        sirenService.delete(1);

        verify(sirenRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdateNewSirenInactive() {
        Location locOld = new Location(34.0, -118.0);
        Location locNew = new Location(30.001, -110.001);

        Siren siren = new Siren();
        siren.setLocation(locOld);
        siren.setActive(true);
        siren.setName("Old");
        siren.setFire(new ArrayList<>());

        Siren updated = new Siren();
        updated.setLocation(locNew);
        updated.setActive(false);
        updated.setName("New");

        Fire nearbyFire = new Fire();
        nearbyFire.setLocation(locNew);
        nearbyFire.setSirens(new ArrayList<>());
        List<Fire> fires = List.of(nearbyFire);

        when(sirenRepository.findById(1)).thenReturn(Optional.of(siren));
        when(fireRepository.findByEndTimeIsNull()).thenReturn(fires);
        when(sirenRepository.save(any(Siren.class))).thenAnswer(i -> i.getArgument(0));

        Siren result = sirenService.update(1, updated);

        assertEquals("New", result.getName());
        assertFalse(result.isActive());
        assertEquals(SirenStatus.SAFE, result.getStatus()); // Expected to be safe since new active status is false
        assertTrue(result.getFire().contains(nearbyFire));
        verify(sirenRepository, times(1)).save(siren);
    }

    @Test
    void testUpdateNewSirenActive() {
        Location locOld = new Location(34.0, -118.0);
        Location locNew = new Location(30.001, -110.001);

        Siren siren = new Siren();
        siren.setLocation(locOld);
        siren.setActive(false);
        siren.setName("Old");
        siren.setFire(new ArrayList<>());

        Siren updated = new Siren();
        updated.setLocation(locNew);
        updated.setActive(true);
        updated.setName("New");

        Fire nearbyFire = new Fire();
        nearbyFire.setLocation(locNew);
        nearbyFire.setSirens(new ArrayList<>());
        List<Fire> fires = List.of(nearbyFire);

        when(sirenRepository.findById(1)).thenReturn(Optional.of(siren));
        when(fireRepository.findByEndTimeIsNull()).thenReturn(fires);
        when(sirenRepository.save(any(Siren.class))).thenAnswer(i -> i.getArgument(0));

        Siren result = sirenService.update(1, updated);

        assertEquals("New", result.getName());
        assertTrue(result.isActive());
        assertEquals(SirenStatus.EMERGENCY, result.getStatus()); // Expected to be safe since new active status is false
        assertTrue(result.getFire().contains(nearbyFire));
        verify(sirenRepository, times(1)).save(siren);
    }

    @Test
    void testUpdateNewSirenNoFire() {
        Location locOld = new Location(34.0, -118.0);
        Location locNew = new Location(30.001, -110.001);

        Fire nearbyFire = new Fire();
        nearbyFire.setLocation(locOld);
        nearbyFire.setSirens(new ArrayList<>());
        List<Fire> fires = List.of(nearbyFire);

        Siren siren = new Siren();
        siren.setLocation(locOld);
        siren.setActive(false);
        siren.setName("Old");
        siren.setFire(new ArrayList<>(fires));

        Siren updated = new Siren();
        updated.setLocation(locNew);
        updated.setActive(true);
        updated.setName("New");

        when(sirenRepository.findById(1)).thenReturn(Optional.of(siren));
        when(fireRepository.findByEndTimeIsNull()).thenReturn(fires);
        when(sirenRepository.save(any(Siren.class))).thenAnswer(i -> i.getArgument(0));

        Siren result = sirenService.update(1, updated);

        assertEquals("New", result.getName());
        assertTrue(result.isActive());
        assertEquals(SirenStatus.SAFE, result.getStatus()); // Expected to be safe since new active status is false
        assertTrue(result.getFire().isEmpty());
        verify(sirenRepository, times(1)).save(siren);
    }
}