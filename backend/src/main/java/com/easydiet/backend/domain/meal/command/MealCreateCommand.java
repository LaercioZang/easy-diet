package com.easydiet.backend.domain.meal.command;

import lombok.Builder;

import java.time.DayOfWeek;

@Builder
public record MealCreateCommand(
        DayOfWeek dayOfWeek,
        String name,
        Integer mealOrder
) {
}
