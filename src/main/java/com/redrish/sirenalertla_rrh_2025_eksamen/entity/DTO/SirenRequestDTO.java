package com.redrish.sirenalertla_rrh_2025_eksamen.entity.DTO;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Location;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Siren;
import com.redrish.sirenalertla_rrh_2025_eksamen.entity.SirenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SirenRequestDTO {
    private double latitude;
    private double longitude;
    private boolean isActive;
    private String name;

    public Siren toSiren() {
        Siren siren = new Siren();
        siren.setLocation(new Location(latitude, longitude));
        siren.setActive(isActive);
        siren.setName(name);
        return siren;
    }
}
