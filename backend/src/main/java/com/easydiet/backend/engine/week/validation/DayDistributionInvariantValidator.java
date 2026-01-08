package com.easydiet.backend.engine.week.validation;

import com.easydiet.backend.engine.week.model.DayDistribution;

public final class DayDistributionInvariantValidator {

    private DayDistributionInvariantValidator() {
    }

    public static void validate(DayDistribution day) {
        if (day.getDayType() == null) {
            throw new IllegalStateException("DayType é obrigatório");
        }

        if (day.getMeals() == null) {
            throw new IllegalStateException("MealDistribution é obrigatório");
        }
    }
}
