package com.easydiet.backend.dto.meal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.DayOfWeek;

public record MealUpdateRequest(

        @NotNull
        DayOfWeek dayOfWeek,

        @NotNull
        String name,

        @NotNull
        @Positive
        Integer mealOrder,

        @NotNull
        @Positive
        Integer calorieTarget,

        @NotNull
        @PositiveOrZero
        BigDecimal proteinTarget,

        @NotNull
        @PositiveOrZero
        BigDecimal carbsTarget,

        @NotNull
        @PositiveOrZero
        BigDecimal fatTarget
) {
}
