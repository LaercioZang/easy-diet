package com.easydiet.backend.domain.diet.totals;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Builder
public record DietDayTotals(
        DayOfWeek day,
        BigDecimal calories,
        BigDecimal protein,
        BigDecimal carbs,
        BigDecimal fat
) {}
