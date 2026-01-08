package com.easydiet.backend.domain.validation;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.DietPlanInvariantValidator;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.meal.model.MealTarget;
import com.easydiet.backend.engine.meal.validation.MealDistributionInvariantValidator;
import com.easydiet.backend.engine.meal.validation.MealTargetInvariantValidator;
import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.engine.week.validation.DayDistributionInvariantValidator;
import com.easydiet.backend.engine.week.validation.WeekDistributionInvariantValidator;

public final class DomainValidationOrchestrator {

    private DomainValidationOrchestrator() {
        // domínio não instancia
    }

    public static void validate(DietPlan dietPlan) {
        DietPlanInvariantValidator.validate(dietPlan);

        WeekDistribution week = dietPlan.getWeekDistribution();
        WeekDistributionInvariantValidator.validate(week);

        for (DayDistribution day : week.getDays()) {
            DayDistributionInvariantValidator.validate(day);

            MealDistribution mealDistribution = day.getMeals();
            MealDistributionInvariantValidator.validate(mealDistribution);

            for (MealTarget target : mealDistribution.getMeals()) {
                MealTargetInvariantValidator.validate(target);
            }
        }
    }
}
