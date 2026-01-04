package com.easydiet.backend.dto.diet;

import java.time.DayOfWeek;
import java.util.List;

public record DayPlanResponse(
        DayOfWeek day,
        boolean trainingDay,
        List<MealResponse> meals
) {}
