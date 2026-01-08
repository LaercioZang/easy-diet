package com.easydiet.backend.engine.meal.validation;

import com.easydiet.backend.engine.meal.model.MealTarget;

public final class MealTargetInvariantValidator {

    private MealTargetInvariantValidator() {
    }

    public static void validate(MealTarget target) {
        if (target.getCalories() <= 0) {
            throw new IllegalStateException("Calorias devem ser maiores que zero");
        }

        if (target.getProteinGrams() < 0 ||
            target.getCarbsGrams() < 0 ||
            target.getFatGrams() < 0) {
            throw new IllegalStateException("Macros não podem ser negativos");
        }
    }
}
