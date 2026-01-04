package com.easydiet.backend.dto.diet;

import java.util.List;

public record DietPlanResponse(
        Integer tdee,
        Integer targetCalories,
        MacrosResponse macros,
        List<DayPlanResponse> days
) {}
