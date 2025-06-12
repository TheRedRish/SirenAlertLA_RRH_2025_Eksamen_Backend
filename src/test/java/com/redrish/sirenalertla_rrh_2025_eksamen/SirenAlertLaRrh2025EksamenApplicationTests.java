package com.redrish.sirenalertla_rrh_2025_eksamen;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Location;
import com.redrish.sirenalertla_rrh_2025_eksamen.util.LocationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SirenAlertLaRrh2025EksamenApplicationTests {

    @Test
    public void testCalculateDistanceKM() {
        Location loc1 = new Location(55.69291824008439, 12.555448469728441); // KEA Nørrebro
        Location loc2 = new Location(55.92910790414711, 12.291596950101706); // KEA Hillerød

        double distance = LocationUtil.calculateDistanceKM(loc1, loc2);

        // Forventet afstand ca. 31 km (afrundet)
        assertEquals(31, distance, 0.1); // Tolerance 0.5 km
    }

    @Test
    public void testCalculateDistanceSameLocation() {
        Location loc = new Location(34.0195, -118.4912);
        double distance = LocationUtil.calculateDistanceKM(loc, loc);
        assertEquals(0.0, distance, 0.0001);
    }
}
