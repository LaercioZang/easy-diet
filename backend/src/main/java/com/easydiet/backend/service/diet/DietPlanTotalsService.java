package com.easydiet.backend.service.diet;

import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;

import java.util.UUID;

public interface DietPlanTotalsService {

    DietPlanTotalsResponse calculateForActivePlan(UUID userId);
}
