package com.easydiet.backend.dto.diet;

public record MealResponse(
        int mealNumber,
        Integer calories,
        MacrosResponse macros
) {}
