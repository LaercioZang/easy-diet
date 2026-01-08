package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.totals.DietPlanTotals;

import java.util.UUID;

public interface DietPlanTotalsService {

    DietPlanTotals calculateForActivePlan(UUID userId);

}
