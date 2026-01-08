package com.easydiet.backend.service;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;
import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.persistence.diet.DietPlanEntity;

import java.util.List;
import java.util.UUID;

public interface DietPlanService {

    DietPlan generate(DietPlanGenerateCommand command, UUID userId);

    List<DietPlanEntity> findAllByUser(UUID userId);

    DietPlanEntity findById(UUID id);

    List<DietPlanEntity> findAll();

    DietPlanEntity activate(UUID planId, UUID userId);

    DietPlanEntity findActive(UUID userId);

    DietPlanTotalsResponse calculateTotals(UUID userId);

}
