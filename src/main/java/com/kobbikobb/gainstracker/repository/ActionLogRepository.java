package com.kobbikobb.gainstracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionLogRepository extends JpaRepository<ActionLogEntity, Long> {
    List<ActionLogEntity> findByActionId(Long actionId);
}
