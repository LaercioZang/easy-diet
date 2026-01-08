package com.easydiet.backend.domain.diet;

import com.easydiet.backend.engine.week.model.WeekDistribution;

import java.util.Objects;

public final class DietPlanInvariantValidator {

    private DietPlanInvariantValidator() {
    }

    public static void validate(DietPlan dietPlan) {
        Objects.requireNonNull(dietPlan, "DietPlan não pode ser null");

        validateTdee(dietPlan);
        validateWeekDistribution(dietPlan.getWeekDistribution());
    }

    private static void validateTdee(DietPlan dietPlan) {
        if (dietPlan.getTdee() <= 0) {
            throw new IllegalStateException("TDEE deve ser maior que zero");
        }
    }

    private static void validateWeekDistribution(WeekDistribution weekDistribution) {
        if (weekDistribution == null) {
            throw new IllegalStateException("WeekDistribution é obrigatório");
        }
    }
}
