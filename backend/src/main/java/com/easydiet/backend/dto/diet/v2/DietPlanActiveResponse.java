package com.easydiet.backend.dto.diet.v2;

import com.easydiet.backend.dto.diet.DietPlanResponse;
import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;

public record DietPlanActiveResponse(
        DietPlanResponse plan,
        DietPlanTotalsResponse totals
) {}
