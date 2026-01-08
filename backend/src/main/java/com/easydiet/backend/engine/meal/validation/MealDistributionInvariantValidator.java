package com.easydiet.backend.engine.meal.validation;

import com.easydiet.backend.engine.meal.model.MealDistribution;

public final class MealDistributionInvariantValidator {

    private MealDistributionInvariantValidator() {
    }

    public static void validate(MealDistribution distribution) {
        if (distribution.getMealsPerDay() <= 0) {
            throw new IllegalStateException("mealsPerDay deve ser maior que zero");
        }

        if (distribution.getMeals() == null || distribution.getMeals().isEmpty()) {
            throw new IllegalStateException("MealDistribution deve conter meals");
        }

        if (distribution.getMealsPerDay() != distribution.getMeals().size()) {
            throw new IllegalStateException(
                    "mealsPerDay deve ser igual à quantidade de MealTarget"
            );
        }
    }
}
