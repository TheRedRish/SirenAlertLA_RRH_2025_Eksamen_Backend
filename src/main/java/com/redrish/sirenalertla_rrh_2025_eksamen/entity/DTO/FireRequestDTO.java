package com.redrish.sirenalertla_rrh_2025_eksamen.entity.DTO;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireRequestDTO {
    private double latitude;
    private double longitude;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Fire toFire() {
        Fire fire = new Fire();
        fire.setLocation(new Location(latitude, longitude));
        fire.setStartTime(startTime);
        fire.setEndTime(endTime);
        return fire;
    }
}
