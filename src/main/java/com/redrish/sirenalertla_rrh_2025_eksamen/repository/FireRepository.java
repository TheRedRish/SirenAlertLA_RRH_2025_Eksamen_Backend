package com.redrish.sirenalertla_rrh_2025_eksamen.repository;

import com.redrish.sirenalertla_rrh_2025_eksamen.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireRepository extends JpaRepository<Fire, Integer> {
    List<Fire> findByEndTimeIsNull();
}