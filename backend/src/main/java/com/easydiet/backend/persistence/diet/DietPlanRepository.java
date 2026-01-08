package com.easydiet.backend.persistence.diet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DietPlanRepository extends JpaRepository<DietPlanEntity, UUID> {

    List<DietPlanEntity> findAllByUser_Id(UUID userId);

    Optional<DietPlanEntity> findByUserIdAndStatus(UUID userId, DietPlanStatus status);

    List<DietPlanEntity> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
}
