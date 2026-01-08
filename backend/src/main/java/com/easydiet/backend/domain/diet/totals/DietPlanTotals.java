package com.easydiet.backend.domain.diet.totals;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record DietPlanTotals(
        BigDecimal totalCalories,
        BigDecimal totalProtein,
        BigDecimal totalCarbs,
        BigDecimal totalFat,
        List<DietDayTotals> days
) {}
