package com.easydiet.backend.domain.meal.command;

import lombok.Builder;

@Builder
public record MealUpdateCommand(
        String name,
        Integer mealOrder
) {
}
