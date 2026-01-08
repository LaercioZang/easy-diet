package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;
import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DietPlanService {

    DietPlan generate(DietPlanGenerateCommand command, UUID userId);

    List<DietPlanEntity> findAllByUser(UUID userId);

    Page<DietPlanEntity> findAllByUser(UUID userId, Pageable pageable);

    DietPlanEntity findById(UUID id);

    List<DietPlanEntity> findAll();

    DietPlanEntity activate(UUID planId, UUID userId);

    DietPlanEntity findActive(UUID userId);
}