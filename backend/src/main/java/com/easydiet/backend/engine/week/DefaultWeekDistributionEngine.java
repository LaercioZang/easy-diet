package com.easydiet.backend.engine.week;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.engine.meal.DefaultMealDistributionEngine;
import com.easydiet.backend.engine.meal.MealDistributionEngine;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.week.model.DayCalorieFactor;
import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.DayType;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class DefaultWeekDistributionEngine
        implements WeekDistributionEngine {

    private final MealDistributionEngine mealEngine =
            new DefaultMealDistributionEngine();

    @Override
    public WeekDistribution distribute(
            NutritionTarget baseTarget,
            int trainingDaysPerWeek,
            int mealsPerDay
    ) {

        if (baseTarget == null) {
            throw new DomainException(ErrorCode.NULL_VALUE);
        }

        if (trainingDaysPerWeek < 0 || trainingDaysPerWeek > 7) {
            throw new DomainException(ErrorCode.OUT_OF_RANGE);
        }

        if (mealsPerDay <= 0) {
            throw new DomainException(ErrorCode.INVALID_MEALS_PER_DAY);
        }

        int restDays = 7 - trainingDaysPerWeek;
        List<DayDistribution> days = new ArrayList<>();

        // Training days
        for (int i = 0; i < trainingDaysPerWeek; i++) {
            MealDistribution meals =
                    mealEngine.distribute(baseTarget, mealsPerDay);

            days.add(DayDistribution.builder()
                    .dayType(DayType.TRAINING)
                    .meals(meals)
                    .build());
        }

        int adjustedCalories =
                (int) Math.round(DayCalorieFactor.REST.apply(baseTarget.getCalories()));

        // Rest days (90% calories)
        NutritionTarget restTarget = NutritionTarget.builder()
                .calories((int) Math.round(adjustedCalories))
                .proteinGrams(baseTarget.getProteinGrams())
                .carbsGrams(baseTarget.getCarbsGrams())
                .fatGrams(baseTarget.getFatGrams())
                .goal(baseTarget.getGoal())
                .build();

        for (int i = 0; i < restDays; i++) {
            MealDistribution meals =
                    mealEngine.distribute(restTarget, mealsPerDay);

            days.add(DayDistribution.builder()
                    .dayType(DayType.REST)
                    .meals(meals)
                    .build());
        }

        return WeekDistribution.builder()
                .trainingDays(trainingDaysPerWeek)
                .restDays(restDays)
                .days(days)
                .build();
    }
}
