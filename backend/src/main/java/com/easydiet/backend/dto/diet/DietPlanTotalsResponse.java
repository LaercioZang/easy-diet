package com.easydiet.backend.dto.diet;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

public record DietPlanTotalsResponse(

        Map<DayOfWeek, DayTotals> dailyTotals,

        BigDecimal weeklyCalories,
        BigDecimal weeklyProtein,
        BigDecimal weeklyCarbs,
        BigDecimal weeklyFat
) {

    public record DayTotals(
            BigDecimal calories,
            BigDecimal protein,
            BigDecimal carbs,
            BigDecimal fat
    ) {}
}
