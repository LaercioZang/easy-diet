package com.easydiet.backend.dto.meal;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.UUID;

public record MealResponse(
        UUID id,
        DayOfWeek dayOfWeek,
        String name,
        Integer mealOrder,

        BigDecimal totalCalories,
        BigDecimal totalProtein,
        BigDecimal totalCarbs,
        BigDecimal totalFat
) {
}
